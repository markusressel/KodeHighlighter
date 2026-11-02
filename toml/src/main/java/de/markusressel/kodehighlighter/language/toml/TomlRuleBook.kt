package de.markusressel.kodehighlighter.language.toml

import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.toml.rule.CommentRule
import de.markusressel.kodehighlighter.language.toml.rule.DateRule
import de.markusressel.kodehighlighter.language.toml.rule.KeyRule
import de.markusressel.kodehighlighter.language.toml.rule.LiteralRule
import de.markusressel.kodehighlighter.language.toml.rule.NumberRule
import de.markusressel.kodehighlighter.language.toml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.toml.rule.StringRule
import de.markusressel.kodehighlighter.language.toml.rule.TableRule

/**
 * TOML rule book
 */
open class TomlRuleBook : RegionRuleBook() {

    override val scanner = TomlRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        TableRule,
        KeyRule,
        QuotedKeyRule,
        LiteralRule,
        DateRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
