package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.markdown.rule.*

class MarkdownSyntaxHighlighter : SyntaxHighlighter {

    override val defaultColorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(
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