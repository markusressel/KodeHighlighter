package de.markusressel.kodehighlighter.language.xml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object EntityRule : LanguageRule {

    override val tokenType = TokenType.CONSTANT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "&(?:[A-Za-z][A-Za-z0-9]*|#\\d+|#[xX][0-9a-fA-F]+);".toRegex()

}
