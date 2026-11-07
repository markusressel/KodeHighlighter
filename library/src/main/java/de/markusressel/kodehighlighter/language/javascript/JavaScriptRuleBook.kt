package de.markusressel.kodehighlighter.language.javascript

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.javascript.rule.CommentRule
import de.markusressel.kodehighlighter.language.javascript.rule.FunctionRule
import de.markusressel.kodehighlighter.language.javascript.rule.KeywordRule
import de.markusressel.kodehighlighter.language.javascript.rule.LiteralRule
import de.markusressel.kodehighlighter.language.javascript.rule.NumberRule
import de.markusressel.kodehighlighter.language.javascript.rule.RegexRule
import de.markusressel.kodehighlighter.language.javascript.rule.StringRule
import de.markusressel.kodehighlighter.language.javascript.rule.TypeRule

/**
 * JavaScript language rule book
 */
open class JavaScriptRuleBook : RegionRuleBook() {

    override val language: Language get() = JavaScriptLanguage

    override val scanner = JavaScriptRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        KeywordRule,
        TypeRule,
        FunctionRule,
        LiteralRule,
        NumberRule,
        StringRule,
        RegexRule,
        CommentRule,
    )

    override fun getRules() = rules

}
