package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

class OCamlRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
                BooleanRule(),
                KeywordRule(),
                ModuleRule(),
                NumberRule(),
                CommentRule(),
                StringRule())
    }

}