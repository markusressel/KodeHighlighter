package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssRegions

/**
 * Strings, and unquoted URLs like in `url(a.png)`
 */
object StringRule : LanguageRule {

    override val tokenType = TokenType.STRING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssRegions.scanner.findMatches(text, this)
    }

}
