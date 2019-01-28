package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle

/**
 * A function that creates a [CharacterStyle] that can be applied to a [Spannable]
 */
typealias StyleFactory = () -> CharacterStyle

/**
 * Interface for a syntax highlighter with basic logic for color schemes and applying styles
 */
interface SyntaxHighlighter {

    /**
     * The currently active color scheme
     */
    var colorScheme: SyntaxColorScheme

    /**
     * Get a set of rules for this highlighter
     */
    fun getRules(): Set<SyntaxHighlighterRule>

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     * @return a list of all [CharacterStyle] instances that were applied
     */
    fun highlight(spannable: Spannable): List<CharacterStyle> {
        return getRules().map { rule ->
            rule.findMatches(spannable).map {
                val start = it.range.start
                val end = it.range.endInclusive + 1

                // needs to be called for each result
                // so multiple spans are created and applied
                val styles = colorScheme.getStyles(rule)

                highlight(spannable, start, end, styles)
            }.flatten()
        }.flatten()
    }

    /**
     * Apply a set of styleFactories to a specific part of an spannable
     *
     * @param spannable the spannable to highlighting
     * @param start the starting position
     * @param end the end position (inclusive)
     * @param styleFactories a set of the style factories to apply
     * @return a list of all applied styles
     */
    private fun highlight(spannable: Spannable, start: Int, end: Int, styleFactories: Set<StyleFactory>): List<CharacterStyle> {
        val stylesToApply = styleFactories.map { it() }

        stylesToApply.forEach {
            spannable.setSpan(it, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return stylesToApply
    }

}