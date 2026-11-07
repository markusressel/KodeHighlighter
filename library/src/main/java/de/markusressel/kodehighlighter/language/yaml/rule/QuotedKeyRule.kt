package de.markusressel.kodehighlighter.language.yaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.yaml.YamlRegions

object QuotedKeyRule : LanguageRule {

    override val tokenType = TokenType.PROPERTY

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return YamlRegions.scanner.findMatches(text, this)
    }

}
