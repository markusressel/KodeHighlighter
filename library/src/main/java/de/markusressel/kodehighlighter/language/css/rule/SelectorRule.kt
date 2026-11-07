package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssSections

/**
 * Classes, ids, pseudo-classes and attributes in selectors
 */
object SelectorRule : LanguageRule {

    override val tokenType = TokenType.ATTRIBUTE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssSections.findMatches(text, PATTERN, selectors = true)
    }

    private val PATTERN = "[.#]-?[A-Za-z_][\\w-]*|::?[A-Za-z-][\\w-]*|\\[[^\\]\\n]{0,200}\\]".toRegex()

}
