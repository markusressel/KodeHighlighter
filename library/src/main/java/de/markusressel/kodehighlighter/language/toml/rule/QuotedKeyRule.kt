package de.markusressel.kodehighlighter.language.toml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.toml.TomlRegions

object QuotedKeyRule : LanguageRule {

    override val tokenType = TokenType.PROPERTY

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return TomlRegions.scanner.findMatches(text, this)
    }

}
