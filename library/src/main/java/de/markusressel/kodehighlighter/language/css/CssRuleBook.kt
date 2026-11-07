package de.markusressel.kodehighlighter.language.css

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.css.rule.ColorRule
import de.markusressel.kodehighlighter.language.css.rule.CommentRule
import de.markusressel.kodehighlighter.language.css.rule.FunctionRule
import de.markusressel.kodehighlighter.language.css.rule.KeywordRule
import de.markusressel.kodehighlighter.language.css.rule.NumberRule
import de.markusressel.kodehighlighter.language.css.rule.PropertyRule
import de.markusressel.kodehighlighter.language.css.rule.SelectorRule
import de.markusressel.kodehighlighter.language.css.rule.StringRule
import de.markusressel.kodehighlighter.language.css.rule.TagSelectorRule

/**
 * CSS language rule book
 */
open class CssRuleBook : RegionRuleBook() {

    override val language: Language get() = CssLanguage

    override val scanner = CssRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        TagSelectorRule,
        SelectorRule,
        PropertyRule,
        FunctionRule,
        ColorRule,
        NumberRule,
        KeywordRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
