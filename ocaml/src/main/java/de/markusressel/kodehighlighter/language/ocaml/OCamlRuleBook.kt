package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.ocaml.rule.*

class OCamlRuleBook : LanguageRuleBook {

    private val rules = listOf(
        BooleanRule,
        KeywordRule,
        ModuleRule,
        NumberRule,
        CommentRule,
        StringRule
    )

    override fun getRules() = rules

}