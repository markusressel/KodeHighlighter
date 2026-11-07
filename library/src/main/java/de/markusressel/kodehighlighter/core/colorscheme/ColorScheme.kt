package de.markusressel.kodehighlighter.core.colorscheme

import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.rule.LanguageRule

/**
 * A color scheme for a language rule book.
 * This essentially maps rules to the styles that will be applied to matched text passages.
 */
interface ColorScheme<Style> {

    /**
     * Get a set of styles to apply for a specific language rule
     */
    fun getStyles(type: LanguageRule): Set<StyleFactory<Style>>

}

/**
 * @param fallback the color scheme for rules this one has no styles for, like those of a language embedded in another
 * one. A [TokenTheme] styles every rule with a token type
 * @return this color scheme, with the styles of the [fallback] for rules this one doesn't style
 */
fun <Style> ColorScheme<Style>.withFallback(fallback: ColorScheme<Style>): ColorScheme<Style> {
    val primary = this
    return object : ColorScheme<Style> {
        override fun getStyles(type: LanguageRule): Set<StyleFactory<Style>> =
            primary.getStyles(type).ifEmpty { fallback.getStyles(type) }
    }
}
