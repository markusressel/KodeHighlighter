package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
     * Analyzes the given text and creates a list of styles that would need to be applied
     * to it
     *
     * @param charSequence the text to analyze
     * @return list of highlighting entities
     */
    suspend fun createHighlighting(charSequence: CharSequence): List<HighlightEntity> {
        return getRules().map { rule ->
            rule.findMatches(charSequence).map {
                val start = it.range.start
                val end = it.range.endInclusive + 1

                // needs to be called for each result
                // so multiple spans are created and applied
                val styleFactories = colorScheme.getStyles(rule)

                HighlightEntity(
                        start = start,
                        end = end,
                        styles = styleFactories
                )
            }
        }.flatten()
    }

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     * @return a list of all [CharacterStyle] instances that were applied
     */
    suspend fun highlight(spannable: Spannable): List<CharacterStyle> {
        val highlightEntities = withContext(Dispatchers.Default) {
            createHighlighting(spannable)
        }

        return highlight(spannable, highlightEntities)
    }

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     * @param highlightEntities a list of [HighlightEntity] objects that hold the styles to apply
     * @return a list of all [CharacterStyle] instances that were applied
     */
    suspend fun highlight(spannable: Spannable, highlightEntities: List<HighlightEntity>): List<CharacterStyle> {
        return highlightEntities.map {
            highlight(spannable, it.start, it.end, it.styles)
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