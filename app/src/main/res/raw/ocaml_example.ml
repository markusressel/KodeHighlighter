(*
* Projet TIPE sur la cryptographie
* et le chiffrement
*
* Partie : AES
*
* Auteurs :
* FALLET Nathan
* LAMIDEL Arthur
* MAKALOU Shérif
*)

(*
* Conversion d'un nombre en polynôme
* Un polynôme est représenté par un tableau
* de coefficents (0 ou 1 pour être dans GF(2))
*)
let polynome n =
    (* On fabrique un polynôme *)
    let p = Array.make 15 0 in
    for k = 0 to 14 do
        (* Coefficient de degré k *)
        p.(k) <- (n lsr k) land 1
    done;
    p;;

(*
* Conversion d'un polynôme en nombre
* On multiplie les coefficients par
* les puissances de 2
*)
let nombre p =
    (* On fait un nombre *)
    let n = ref 0 in
    for k = 0 to 14 do
        (* On multiplie le coefficient par la puissance de 2 *)
        n := !n + (p.(k) lsl k)
    done;
    !n;;

(*
* Somme de deux polynômes
*)
let somme p1 p2 =
    (* On fabrique un polynôme *)
    let p = Array.make 15 0 in
    for k = 0 to 14 do
        (* Somme des coefficients de degré k *)
        p.(k) <- (p1.(k) lxor p2.(k))
    done;
    p;;

(*
* Degré d'un polynôme
*)
let degre p =
    let l = Array.length p in
    let d = ref 0 in
    for k = 0 to l-1 do
        if p.(k) = 1 then d := k
    done;
    !d;;

(*
* Produit de deux polynômes
*)
let produit p1 p2 =
    (* On fabrique un polynôme *)
    let p = Array.make 15 0 in
    for k = 0 to 14 do
        (* Coefficient de degré k *)
        for i = 0 to k do
            (* La somme des coefficients de degré i et j avec i + j = k *)
            p.(k) <- p.(k) lxor (p1.(i) land p2.(k-i))
        done
    done;
    p;;

(*
* Reste de la division euclidienne de
* deux polynômes
*)
let rec reste dividende diviseur =
    (* Degrés des polynômes *)
    let d1 = degre dividende in
    let d2 = degre diviseur in
    (* On regarde lequel a le plus grand degré *)
    if d1 >= d2 then
        (*
        * Si c'est le dividende, on multiplie le diviseur par x à la
        * puissance la différence des dégrées, et on soustrait ce résultat
        * au dividende. Le reste de p1 par p2 est donc récursivement le reste
        * de la disivion de ce nouveau polynôme par p2.
        *)
        let quotient = polynome (1 lsl (d1-d2)) in
        let cequonsoustrait = produit diviseur quotient in
        let cequonredivise = somme dividende cequonsoustrait in
        reste cequonredivise diviseur
    else
        (* Sinon on ne peut pas diviser et alors le dividende est le reste *)
        dividende;;

(*
* Polynôme irréductible de GF(2^8)
*)
let irreductible = [|1; 1; 0; 1; 1; 0; 0; 0; 1; 0; 0; 0; 0; 0; 0|];;

(*
* Produit de deux nombres dans GF(2^8)
*)
let fois a b =
    let p1 = polynome a in
    let p2 = polynome b in
    let p = produit p1 p2 in
    let r = reste p irreductible in
    nombre r;;

(*
* La matrice utilisée pour faire le produit
* des colonnes (constante)
*)
let m = [|
    2;  3;  1;  1;
    1;  2;  3;  1;
    1;  1;  2;  3;
    3;  1;  1;  2
|];;

(*
* On défini le produit de deux matrices
* On va uniquement se concentrer sur le cas
* du produit pour le mixage des colonnes dont
* la taille des matrices est fixé
*)
let produit_colonne col =
    (* On fabrique la colonne de sortie *)
    let resultat = Array.make 4 0 in
    for i = 0 to 3 do
        (*
        * Chaque coefficient est la somme des
        * produits des éléments d'une ligne
        * avec ceux d'une colonne
        *)
        for k = 0 to 3 do
            resultat.(i) <- (fois m.(i*4 + k) col.(k)) lxor resultat.(i)
        done
    done;
    resultat;;

(*
* Table de substitution
*)
let sbox = [|
    0x63; 0x7c; 0x77; 0x7b; 0xf2; 0x6b; 0x6f; 0xc5; 0x30; 0x01; 0x67; 0x2b; 0xfe; 0xd7; 0xab; 0x76;
    0xca; 0x82; 0xc9; 0x7d; 0xfa; 0x59; 0x47; 0xf0; 0xad; 0xd4; 0xa2; 0xaf; 0x9c; 0xa4; 0x72; 0xc0;
    0xb7; 0xfd; 0x93; 0x26; 0x36; 0x3f; 0xf7; 0xcc; 0x34; 0xa5; 0xe5; 0xf1; 0x71; 0xd8; 0x31; 0x15;
    0x04; 0xc7; 0x23; 0xc3; 0x18; 0x96; 0x05; 0x9a; 0x07; 0x12; 0x80; 0xe2; 0xeb; 0x27; 0xb2; 0x75;
    0x09; 0x83; 0x2c; 0x1a; 0x1b; 0x6e; 0x5a; 0xa0; 0x52; 0x3b; 0xd6; 0xb3; 0x29; 0xe3; 0x2f; 0x84;
    0x53; 0xd1; 0x00; 0xed; 0x20; 0xfc; 0xb1; 0x5b; 0x6a; 0xcb; 0xbe; 0x39; 0x4a; 0x4c; 0x58; 0xcf;
    0xd0; 0xef; 0xaa; 0xfb; 0x43; 0x4d; 0x33; 0x85; 0x45; 0xf9; 0x02; 0x7f; 0x50; 0x3c; 0x9f; 0xa8;
    0x51; 0xa3; 0x40; 0x8f; 0x92; 0x9d; 0x38; 0xf5; 0xbc; 0xb6; 0xda; 0x21; 0x10; 0xff; 0xf3; 0xd2;
    0xcd; 0x0c; 0x13; 0xec; 0x5f; 0x97; 0x44; 0x17; 0xc4; 0xa7; 0x7e; 0x3d; 0x64; 0x5d; 0x19; 0x73;
    0x60; 0x81; 0x4f; 0xdc; 0x22; 0x2a; 0x90; 0x88; 0x46; 0xee; 0xb8; 0x14; 0xde; 0x5e; 0x0b; 0xdb;
    0xe0; 0x32; 0x3a; 0x0a; 0x49; 0x06; 0x24; 0x5c; 0xc2; 0xd3; 0xac; 0x62; 0x91; 0x95; 0xe4; 0x79;
    0xe7; 0xc8; 0x37; 0x6d; 0x8d; 0xd5; 0x4e; 0xa9; 0x6c; 0x56; 0xf4; 0xea; 0x65; 0x7a; 0xae; 0x08;
    0xba; 0x78; 0x25; 0x2e; 0x1c; 0xa6; 0xb4; 0xc6; 0xe8; 0xdd; 0x74; 0x1f; 0x4b; 0xbd; 0x8b; 0x8a;
    0x70; 0x3e; 0xb5; 0x66; 0x48; 0x03; 0xf6; 0x0e; 0x61; 0x35; 0x57; 0xb9; 0x86; 0xc1; 0x1d; 0x9e;
    0xe1; 0xf8; 0x98; 0x11; 0x69; 0xd9; 0x8e; 0x94; 0x9b; 0x1e; 0x87; 0xe9; 0xce; 0x55; 0x28; 0xdf;
    0x8c; 0xa1; 0x89; 0x0d; 0xbf; 0xe6; 0x42; 0x68; 0x41; 0x99; 0x2d; 0x0f; 0xb0; 0x54; 0xbb; 0x16
|];;

(*
* Constantes de tours
* Corresponds aux puissances de X de -1 à 9
*)
let rc = [|0x8d; 0x01; 0x02; 0x04; 0x08; 0x10; 0x20; 0x40; 0x80; 0x1b; 0x36|];;

(*
* Substitution
* On remplace chaque élément du tableau par son image dans la substitution box
*)
let substitution entree =
    [|
        sbox.(entree.(0)); sbox.(entree.(1)); sbox.(entree.(2)); sbox.(entree.(3));
        sbox.(entree.(4)); sbox.(entree.(5)); sbox.(entree.(6)); sbox.(entree.(7));
        sbox.(entree.(8)); sbox.(entree.(9)); sbox.(entree.(10)); sbox.(entree.(11));
        sbox.(entree.(12)); sbox.(entree.(13)); sbox.(entree.(14)); sbox.(entree.(15))
    |];;

(*
* Décalage
* On permutte simplement l'emplacement des éléments dans le tableau
*)
let decalage entree =
    [|
        (* 0 <- *)      (* 1 <- *)      (* 2 <- *)      (* 3 <- *)
        entree.(0);     entree.(5);     entree.(10);    entree.(15);
        entree.(4);     entree.(9);     entree.(14);    entree.(3);
        entree.(8);     entree.(13);    entree.(2);     entree.(7);
        entree.(12);    entree.(1);     entree.(6);     entree.(11)
    |];;

(*
* Mixage
* On fait les produits des colonnes pour mélanger le contenu
*)
let mixage entree =
    let sortie = Array.make 16 0 in
    for i = 0 to 3 do
        (* On extrait la colonne *)
        let colonne = [|
            entree.(i*4);
            entree.(i*4 + 1);
            entree.(i*4 + 2);
            entree.(i*4 + 3)
        |] in

        (* On fait le produit et on place les coefficients *)
        let nouvelle_colonne = produit_colonne colonne in
        sortie.(i*4) <- nouvelle_colonne.(0);
        sortie.(i*4 + 1) <- nouvelle_colonne.(1);
        sortie.(i*4 + 2) <- nouvelle_colonne.(2);
        sortie.(i*4 + 3) <- nouvelle_colonne.(3)
    done;
    sortie;;

(*
* Ajout de la clé
* On fait l'addition avec la clé du tour
* L'addition mudulo 2 correspond à un ou exclusif (lxor)
*)
let ajout entree cle =
    [|
        entree.(0) lxor cle.(0); entree.(1) lxor cle.(1); entree.(2) lxor cle.(2); entree.(3) lxor cle.(3);
        entree.(4) lxor cle.(4); entree.(5) lxor cle.(5); entree.(6) lxor cle.(6); entree.(7) lxor cle.(7);
        entree.(8) lxor cle.(8); entree.(9) lxor cle.(9); entree.(10) lxor cle.(10); entree.(11) lxor cle.(11);
        entree.(12) lxor cle.(12); entree.(13) lxor cle.(13); entree.(14) lxor cle.(14); entree.(15) lxor cle.(15)
    |];;

(*
* On calcul la clé du tour suivant
*)
let clef_suivante cle r =
    (* On défini une clé vierge *)
    let nouvelle_cle = Array.make 16 0 in

    (* On calcul les coefficients de la première colonne *)
    nouvelle_cle.(0) <- cle.(0) lxor sbox.(cle.(13)) lxor rc.(r);
    nouvelle_cle.(1) <- cle.(1) lxor sbox.(cle.(14));
    nouvelle_cle.(2) <- cle.(2) lxor sbox.(cle.(15));
    nouvelle_cle.(3) <- cle.(3) lxor sbox.(cle.(12));

    (* On calcul tous les autres coefficients *)
    for k = 4 to 15 do
        nouvelle_cle.(k) <- cle.(k) lxor nouvelle_cle.(k-4)
    done;
    nouvelle_cle;;

(*
* Tour
* On combine les étapes
*)
let rec tour entree cle n =
    (* On calcul la nouvelle clé *)
    let nouvelle_cle = clef_suivante cle (11-n) in

    match n with
    (* Dernier tour, sans le mixage *)
    | 1 -> ajout (decalage (substitution entree)) nouvelle_cle

    (* Tour normal, qu'on envoie au tour suivant *)
    | _ -> tour (ajout (mixage (decalage (substitution entree))) nouvelle_cle) nouvelle_cle (n-1);;

(*
* On fait passer les données par les tours
*)
let chiffrer entree cle =
    (* On ajoute la cle initiale *)
    let keyed = ajout entree cle in

    (*
    * On lance les tours
    * On se fixe sur une clé de taille 128 bits (16 octets)
    * donc on aura toujours 10 tours
    *)
    tour keyed cle 10;;

(*
* Fonction utile pour la démonstration
* On affiche un array en héxadécimal
*)
let print_array array =
	let str = ref "" in
    for k = 0 to 15 do
        str := !str ^ (Printf.sprintf "%02x" array.(k));
    done;
    print_endline !str;;

(*
* On fait notre démonstration
*
* Clé    : 2b7e151628aed2a6abf7158809cf4f3c
* Entrée : 6bc1bee22e409f96e93d7e117393172a
* Sortie : 3ad77bb40d7a3660a89ecaf32466ef97
*
* Vérifie les données de test :
* National Institute of Standards and Technology Special Publication 800-38A 2001 ED
* https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38a.pdf
* Example Vectors for Modes of Operation of the AES - ECB Example Vectors - Block #1
*)
let cle = [|0x2b; 0x7e; 0x15; 0x16; 0x28; 0xae; 0xd2; 0xa6; 0xab; 0xf7; 0x15; 0x88; 0x09; 0xcf; 0x4f; 0x3c|] in
let entree = [|0x6b; 0xc1; 0xbe; 0xe2; 0x2e; 0x40; 0x9f; 0x96; 0xe9; 0x3d; 0x7e; 0x11; 0x73; 0x93; 0x17; 0x2a|] in
let sortie = chiffrer entree cle in

print_endline "Cle    : ";
print_array cle;
print_endline "Entree : ";
print_array entree;
print_endline "Sortie : ";
print_array sortie;;
