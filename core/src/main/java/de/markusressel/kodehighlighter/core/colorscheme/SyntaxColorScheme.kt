package de.markusressel.kodehighlighter.core.colorscheme

import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule

/**
 * A color scheme for a syntax highlighter.
 * This essentially maps rules to the styles that will be applied to matched text passages.
 */
interface SyntaxColorScheme {

    /**
     * Get a set of styles to apply for a specific text/section type
     */
    fun getStyles(type: SyntaxHighlighterRule): Set<StyleFactory>

}
