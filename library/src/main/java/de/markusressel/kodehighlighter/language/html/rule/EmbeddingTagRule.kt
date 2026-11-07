package de.markusressel.kodehighlighter.language.html.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.html.HtmlRegions

/**
 * The `>` that ends the start tag of a `script` or `style` element, whose content is JavaScript or CSS
 */
object EmbeddingTagRule : LanguageRule {

    override val tokenType = TokenType.TAG

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return HtmlRegions.scanner.findMatches(text, this)
    }

}
