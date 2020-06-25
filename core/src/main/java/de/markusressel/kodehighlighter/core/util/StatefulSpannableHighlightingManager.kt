package de.markusressel.kodehighlighter.core.util

import android.text.Spannable
import android.text.style.CharacterStyle
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme

/**
 * A wrapper around a [SyntaxHighlighter] that adds a state to it so previous
 * highlighting states can be cleared before applying a new one.
 */
open class StatefulSpannableHighlightingManager(syntaxHighlighter: SyntaxHighlighter, colorScheme: SyntaxColorScheme)
    : SpannableHighlightingManager(syntaxHighlighter, colorScheme) {

    /**
     * A set containing all currently applied styles to the [Spannable]
     */
    open val appliedStyles = mutableSetOf<CharacterStyle>()

    override fun applyStyle(style: CharacterStyle, spannable: Spannable, start: Int, end: Int) {
        super.applyStyle(style, spannable, start, end)
        appliedStyles.add(style)
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