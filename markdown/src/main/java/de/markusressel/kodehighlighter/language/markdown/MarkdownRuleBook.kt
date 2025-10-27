package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeLineRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.StrikeRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule

/**
 * Markdown language rule book
 */
open class MarkdownRuleBook : LanguageRuleBook {

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