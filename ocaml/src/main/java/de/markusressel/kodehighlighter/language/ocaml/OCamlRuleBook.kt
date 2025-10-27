package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

open class OCamlRuleBook : LanguageRuleBook {

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