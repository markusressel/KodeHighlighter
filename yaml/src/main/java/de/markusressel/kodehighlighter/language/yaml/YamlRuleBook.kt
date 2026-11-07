package de.markusressel.kodehighlighter.language.yaml

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.yaml.rule.AnchorRule
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.DocumentRule
import de.markusressel.kodehighlighter.language.yaml.rule.KeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.ListRule
import de.markusressel.kodehighlighter.language.yaml.rule.LiteralRule
import de.markusressel.kodehighlighter.language.yaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.yaml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.StringRule

/**
 * YAML rule book. Block scalars are highlighted like plain text
 */
open class YamlRuleBook : RegionRuleBook() {

    override val language: Language get() = YamlLanguage

    override val scanner = YamlRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        KeyRule,
        QuotedKeyRule,
        DocumentRule,
        ListRule,
        AnchorRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
