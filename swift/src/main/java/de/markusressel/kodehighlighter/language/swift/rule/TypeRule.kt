package de.markusressel.kodehighlighter.language.swift.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object TypeRule : LanguageRule {

    override val tokenType = TokenType.TYPE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:Any|Array|Bool|Character|Dictionary|Double|Float|Int|Int8|Int16|Int32|Int64|Optional|Set|String|UInt|UInt8|UInt16|UInt32|UInt64|Void)\\b".toRegex()

}
