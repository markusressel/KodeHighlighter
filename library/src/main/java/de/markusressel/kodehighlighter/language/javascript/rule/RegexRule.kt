package de.markusressel.kodehighlighter.language.javascript.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRegions

/**
 * Regular expression literals, like `/a+/g`
 */
object RegexRule : LanguageRule {

    override val tokenType = TokenType.STRING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return JavaScriptRegions.scanner.findMatches(text, this)
    }

}
