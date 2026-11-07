package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssSections

object FunctionRule : LanguageRule {

    override val tokenType = TokenType.FUNCTION

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssSections.findMatches(text, PATTERN, selectors = false)
    }

    private val PATTERN = "(?<![\\w-])-?[A-Za-z_][\\w-]*(?=\\()".toRegex()

}
