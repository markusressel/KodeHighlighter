package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.markdown.rule.*

/**
 * Markdown language rule book
 */
class MarkdownRuleBook : LanguageRuleBook {

    private val rules = listOf(
        HeadingRule,
        ItalicRule,
        BoldRule,
        CodeInlineRule,
        CodeLineRule,
        TextLinkRule,
        ImageLinkRule,
        StrikeRule,
        ListRule
    )

    override fun getRules() = rules

}