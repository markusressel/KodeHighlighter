package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.language.Language

/**
 * OCaml, the language of [OCamlRuleBook]
 */
object OCamlLanguage : Language(
    id = "ocaml",
    displayName = "OCaml",
    fileExtensions = setOf("ml", "mli"),
    mimeTypes = setOf("text/x-ocaml"),
    aliases = setOf("ml", "tuareg"),
    interpreters = setOf("ocaml"),
)
