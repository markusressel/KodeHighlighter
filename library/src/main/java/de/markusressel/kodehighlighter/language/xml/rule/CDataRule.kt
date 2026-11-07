package de.markusressel.kodehighlighter.language.xml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.xml.XmlRegions

object CDataRule : LanguageRule {

    override val tokenType = TokenType.CODE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return XmlRegions.scanner.findMatches(text, this)
    }

}
