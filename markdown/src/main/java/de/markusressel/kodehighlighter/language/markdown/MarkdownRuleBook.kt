package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
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
class MarkdownRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
                HeadingRule(),
                ItalicRule(),
                BoldRule(),
                CodeInlineRule(),
                CodeLineRule(),
                TextLinkRule(),
                ImageLinkRule(),
                StrikeRule(),
                ListRule())
    }

}