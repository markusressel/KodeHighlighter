package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.json.rule.CommentRule
import de.markusressel.kodehighlighter.language.json.rule.KeywordRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.PropertyRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * JSON language rule book
 */
open class JsonRuleBook : RegionRuleBook() {

    override val language: Language get() = JsonLanguage

    override val scanner = JsonRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        NumericRule,
        KeywordRule,
        PropertyRule,
        StringRule,
        CommentRule
    )

    override fun getRules() = rules

}
