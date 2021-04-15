package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.json.rule.KeywordRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * JSON language rule book
 */
class JsonRuleBook : LanguageRuleBook {

    override val targetLanguages = setOf(JsonLanguage)
    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<de.markusressel.kodehighlighter.core.rule.LanguageRule> {
        return setOf(
                StringRule(),
                NumericRule(),
                KeywordRule()
        )
    }

}