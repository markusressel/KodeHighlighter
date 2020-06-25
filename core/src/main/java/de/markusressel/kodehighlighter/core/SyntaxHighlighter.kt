package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

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
     * to it.
     *
     * This combines the given set of rules with the currently set [colorScheme].
     *
     * @param charSequence the text to analyze
     * @return list of highlighting entities
     */
    suspend fun createHighlighting(charSequence: CharSequence): List<HighlightEntity> {
        return coroutineScope {
            getRules().map { rule ->
                async {
                    val matches = rule.findMatches(charSequence)
                    if (matches.isNotEmpty()) {
                        HighlightEntity(
                                rule = rule,
                                matches = matches)
                    } else {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     */
    suspend fun highlight(spannable: Spannable) {
        val highlightEntities = createHighlighting(spannable)
        highlight(spannable, highlightEntities)
    }

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     * @param highlightEntities a list of [HighlightEntity] objects that hold the styles to apply
     */
    suspend fun highlight(spannable: Spannable, highlightEntities: List<HighlightEntity>) {
        coroutineScope {
            highlightEntities.map {
                async {
                    val styleFactories = colorScheme.getStyles(it.rule)
                    it.matches.map { match ->
                        highlight(spannable, match.startIndex, match.endIndex, styleFactories)
                    }
                }
            }.awaitAll()
        }
    }

    /**
     * Apply a set of styleFactories to a specific part of an spannable
     *
     * @param spannable the spannable to highlighting
     * @param start the starting position
     * @param end the endIndex position (inclusive)
     * @param styleFactories a set of the style factories to apply
     */
    fun highlight(spannable: Spannable, start: Int, end: Int, styleFactories: Set<StyleFactory>) {
        styleFactories.forEach {
            applyStyle(it(), spannable, start, end)
        }
    }

    /**
     * Applies the given style to a spannable at a specific position
     */
    fun applyStyle(style: CharacterStyle, spannable: Spannable, start: Int, end: Int) {
        spannable.setSpan(style, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

}