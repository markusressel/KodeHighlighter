package de.markusressel.kodehighlighter.language.yaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object LiteralRule : LanguageRule {

    override val tokenType = TokenType.CONSTANT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?mi)(?<=[:-][ \\t])(?:true|false|null|yes|no|on|off|~)(?=[ \\t]*(?:#|\$))".toRegex()

}
