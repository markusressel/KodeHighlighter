package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.json.rule.KeywordRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * JSON language rule book
 */
class JsonRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
                StringRule(),
                NumericRule(),
                KeywordRule()
        )
    }

}