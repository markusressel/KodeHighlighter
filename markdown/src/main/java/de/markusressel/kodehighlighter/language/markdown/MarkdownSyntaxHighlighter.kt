package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.markdown.rule.*
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterBase
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class MarkdownSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(HeadingRule(), ItalicRule(), BoldRule(), CodeInlineRule(), CodeLineRule(), TextLinkRule(), ImageLinkRule(), StrikeRule())
    }

    override fun getDefaultColorScheme(): SyntaxColorScheme {
        return DarkBackgroundColorScheme()
    }

}