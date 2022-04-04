package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.python.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.python.rule.*

/**
 * Python language rule book
 */
class PythonRuleBook : LanguageRuleBook {

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

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