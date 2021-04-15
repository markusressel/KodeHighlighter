package de.markusressel.kodehighlighter.language.generic

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.generic.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.generic.rule.*

/**
 * Markdown language rule book
 */
class GenericRuleBook : LanguageRuleBook {

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<LanguageRule> {
        return setOf(
                FunctionCallRule(),
                EscapeCharRule(),
                NumericRule(),
                KeywordRule(),
                ConstantsRule(),
                OperatorRule(),
                CommentRule()
        )
    }

}