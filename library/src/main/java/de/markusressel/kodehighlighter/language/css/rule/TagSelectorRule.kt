package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssSections

/**
 * Element names in selectors, like `a` in `a:hover`
 */
object TagSelectorRule : LanguageRule {

    override val tokenType = TokenType.TAG

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssSections.findMatches(text, PATTERN, selectors = true)
    }

    private val PATTERN = "(?<![\\w.#:@%-])(?:[A-Za-z][\\w-]*|\\*)(?![^\\[\\]\\n]{0,200}\\])".toRegex()

}
