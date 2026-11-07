package de.markusressel.kodehighlighter.language.generic

import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.generic.rule.CommentRule
import de.markusressel.kodehighlighter.language.generic.rule.FunctionRule
import de.markusressel.kodehighlighter.language.generic.rule.KeywordRule
import de.markusressel.kodehighlighter.language.generic.rule.LiteralRule
import de.markusressel.kodehighlighter.language.generic.rule.NumberRule
import de.markusressel.kodehighlighter.language.generic.rule.StringRule

/**
 * A rule book for languages without one of their own: `//`, `#` and `/* */` comments, strings, numbers, function
 * names and keywords that many languages share. It has no [language], so nothing detects it, use it as a fallback:
 *
 * ```kotlin
 * val ruleBook = KodeHighlighterLanguages.detectRuleBook(fileName, text) ?: GenericRuleBook()
 * ```
 */
open class GenericRuleBook : RegionRuleBook() {

    override val scanner = GenericRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        FunctionRule,
        KeywordRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
