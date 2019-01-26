package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.StatefulSyntaxHighlighter
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.markdown.rule.*

class MarkdownSyntaxHighlighter : StatefulSyntaxHighlighter() {

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(HeadingRule(), ItalicRule(), BoldRule(), CodeInlineRule(), CodeLineRule(), TextLinkRule(), ImageLinkRule(), StrikeRule())
    }

    override fun getDefaultColorScheme(): SyntaxColorScheme {
        return DarkBackgroundColorScheme()
    }

}