package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.python.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.CommentRule
import de.markusressel.kodehighlighter.language.python.rule.DecoratorRule
import de.markusressel.kodehighlighter.language.python.rule.DefKeyword
import de.markusressel.kodehighlighter.language.python.rule.ExceptRule
import de.markusressel.kodehighlighter.language.python.rule.InternalFunctionsRule
import de.markusressel.kodehighlighter.language.python.rule.MagicRule
import de.markusressel.kodehighlighter.language.python.rule.OtherKeywordsRule
import de.markusressel.kodehighlighter.language.python.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.SelfKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.TypeKeywordRule

/**
 * Python language rule book
 */
class PythonRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
                ClassKeywordRule(),
                DecoratorRule(),
                DefKeyword(),
                ExceptRule(),
                InternalFunctionsRule(),
                MagicRule(),
                OtherKeywordsRule(),
                ReturnKeywordRule(),
                SelfKeywordRule(),
                TypeKeywordRule(),
                CommentRule())
    }

}