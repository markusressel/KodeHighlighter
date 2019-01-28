package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle

/**
 * A wrapper around a [SyntaxHighlighter] that adds a state so previous
 * highlighting states can be cleared before applying a new one.
 *
 * This can be used when using a [SyntaxHighlighter] with f.ex. an [android.text.Editable].
 */
open class StatefulSyntaxHighlighter(syntaxHighlighter: SyntaxHighlighter) : SyntaxHighlighter by syntaxHighlighter {

    /**
     * A set containing all currently applied styles to the [Spannable]
     */
    internal val appliedStyles: MutableSet<CharacterStyle> = mutableSetOf()

    override fun highlight(spannable: Spannable): List<CharacterStyle> {
        clearAppliedStyles(spannable)

        val addedStyles = super.highlight(spannable)

        // remember what styles were applied
        appliedStyles.addAll(addedStyles)

        return addedStyles
    }

    /**
     * Clear any style modifications the syntax highlighter may have made to the [spannable].
     *
     * @param spannable the spannable [Spannable] to clear
     */
    internal fun clearAppliedStyles(spannable: Spannable) {
        appliedStyles.forEach {
            spannable.removeSpan(it)
        }

        appliedStyles.clear()
    }

}