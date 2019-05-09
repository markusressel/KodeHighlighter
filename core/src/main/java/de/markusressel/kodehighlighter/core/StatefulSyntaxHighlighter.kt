package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle

/**
 * A wrapper around a [SyntaxHighlighter] that adds a state to it so previous
 * highlighting states can be cleared before applying a new one.
 */
open class StatefulSyntaxHighlighter(syntaxHighlighter: SyntaxHighlighter)
    : SyntaxHighlighter by syntaxHighlighter {

    /**
     * A set containing all currently applied styles to the [Spannable]
     */
    open val appliedStyles: MutableSet<CharacterStyle> = mutableSetOf()

    override suspend fun highlight(spannable: Spannable, highlightEntities: List<HighlightEntity>): List<CharacterStyle> {
        val addedStyles = super.highlight(spannable, highlightEntities)
        appliedStyles.addAll(addedStyles)
        return addedStyles
    }

    /**
     * Clear any style modifications the syntax highlighter may have made to the [spannable] based
     * on the current set of [appliedStyles].
     *
     * @param spannable the spannable [Spannable] to clear
     * @param resetState true to reset [appliedStyles] state afterwards, false otherwise
     */
    open fun clearAppliedStyles(spannable: Spannable, resetState: Boolean = true) {
        appliedStyles.forEach {
            spannable.removeSpan(it)
        }

        if (resetState) {
            resetState()
        }
    }

    /**
     * Resets the internal state of [appliedStyles].
     */
    open fun resetState() {
        appliedStyles.clear()
    }

}