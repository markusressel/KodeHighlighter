package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.python.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.CommentRule
import de.markusressel.kodehighlighter.language.python.rule.DecoratorRule
import de.markusressel.kodehighlighter.language.python.rule.DefKeyword
import de.markusressel.kodehighlighter.language.python.rule.ExceptRule
import de.markusressel.kodehighlighter.language.python.rule.InternalFunctionsRule
import de.markusressel.kodehighlighter.language.python.rule.MagicRule
import de.markusressel.kodehighlighter.language.python.rule.NumberRule
import de.markusressel.kodehighlighter.language.python.rule.OtherKeywordsRule
import de.markusressel.kodehighlighter.language.python.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.SelfKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.StringRule
import de.markusressel.kodehighlighter.language.python.rule.TypeKeywordRule

/**
 * Python language rule book
 */
open class PythonRuleBook : RegionRuleBook() {

    override val language: Language get() = PythonLanguage

    override val scanner = PythonRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        ClassKeywordRule,
        DecoratorRule,
        DefKeyword,
        ExceptRule,
        InternalFunctionsRule,
        MagicRule,
        OtherKeywordsRule,
        ReturnKeywordRule,
        SelfKeywordRule,
        TypeKeywordRule,
        NumberRule,
        StringRule,
        CommentRule
    )

    override fun getRules() = rules

}
