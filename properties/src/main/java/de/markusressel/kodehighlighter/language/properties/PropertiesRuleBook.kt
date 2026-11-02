package de.markusressel.kodehighlighter.language.properties

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.properties.rule.CommentRule
import de.markusressel.kodehighlighter.language.properties.rule.KeyRule
import de.markusressel.kodehighlighter.language.properties.rule.ValueRule

/**
 * Java properties rule book
 */
open class PropertiesRuleBook : LanguageRuleBook {

    private val rules: List<LanguageRule> = listOf(
        KeyRule,
        ValueRule,
        CommentRule,
    )

    override fun getRules() = rules

}
