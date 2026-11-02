package de.markusressel.kodehighlighter.language.yaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?m)(?<=[:-][ \\t])[+-]?(?:0x[0-9a-fA-F]+|0o[0-7]+|\\d[\\d_]*(?:\\.\\d+)?(?:[eE][+-]?\\d+)?|\\.inf|\\.nan)(?=[ \\t]*(?:#|\$))".toRegex()

}
