package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

/**
 * OCaml language rule book
 */
open class OCamlRuleBook : RegionRuleBook() {

    override val language: Language get() = OCamlLanguage

    override val scanner = OCamlRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        BooleanRule,
        KeywordRule,
        ModuleRule,
        NumberRule,
        StringRule,
        CommentRule
    )

    override fun getRules() = rules

}
