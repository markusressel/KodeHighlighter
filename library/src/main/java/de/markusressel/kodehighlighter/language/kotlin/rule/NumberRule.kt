package de.markusressel.kodehighlighter.language.kotlin.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w.])(?:0[xX][0-9a-fA-F](?:_*[0-9a-fA-F])*+[uU]?[lL]?|0[bB][01](?:_*[01])*+[uU]?[lL]?|\\d(?:_*\\d)*(?:\\.\\d(?:_*\\d)*)?(?:[eE][+-]?\\d(?:_*\\d)*)?(?:[fF]|[uU]?[lL]?)|\\.\\d(?:_*\\d)*(?:[eE][+-]?\\d+)?[fF]?)(?!\\w)".toRegex()

}
