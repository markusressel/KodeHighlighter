package de.markusressel.kodehighlighter.core.util

import android.text.Spannable
import android.text.style.CharacterStyle
import de.markusressel.kodehighlighter.core.HighlightEntity
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Manages the interaction between a [Spannable] and a [SyntaxHighlighter]
 */
open class SpannableHighlightingManager(
        private val syntaxHighlighter: SyntaxHighlighter,
        private val colorScheme: SyntaxColorScheme = syntaxHighlighter.defaultColorScheme)
    : SyntaxHighlighter by syntaxHighlighter {

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     */
    open suspend fun highlight(spannable: Spannable) {
        val highlightEntities = syntaxHighlighter.createHighlighting(spannable)
        highlight(spannable, highlightEntities)
    }

    /**
     * Highlight the given text
     *
     * @param spannable the [Spannable] to apply highlighting to
     * @param highlightEntities a list of [HighlightEntity] objects that hold the styles to apply
     */
    open suspend fun highlight(spannable: Spannable, highlightEntities: List<HighlightEntity>) {
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
    open fun highlight(spannable: Spannable, start: Int, end: Int, styleFactories: Set<StyleFactory>) {
        styleFactories.forEach {
            applyStyle(it(), spannable, start, end)
        }
    }

    /**
     * Applies the given style to a spannable at a specific position
     */
    open fun applyStyle(style: CharacterStyle, spannable: Spannable, start: Int, end: Int) {
        spannable.setSpan(style, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}