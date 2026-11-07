package de.markusressel.kodehighlighter.language.go

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.go.rule.CommentRule
import de.markusressel.kodehighlighter.language.go.rule.KeywordRule
import de.markusressel.kodehighlighter.language.go.rule.LiteralRule
import de.markusressel.kodehighlighter.language.go.rule.NumberRule
import de.markusressel.kodehighlighter.language.go.rule.StringRule
import de.markusressel.kodehighlighter.language.go.rule.TypeRule

/**
 * Go language rule book
 */
open class GoRuleBook : RegionRuleBook() {

    override val language: Language get() = GoLanguage

    override val scanner = GoRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        KeywordRule,
        TypeRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
