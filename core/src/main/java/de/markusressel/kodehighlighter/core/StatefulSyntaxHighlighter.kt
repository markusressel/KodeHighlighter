package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle

/**
 * Implements the [SyntaxHighlighter] interface and adds a state so previous
 * highlighting states can be cleared before applying a new one.
 */
abstract class StatefulSyntaxHighlighter : SyntaxHighlighter {

    /**
     * A set containing all currently applied styles to the [target]
     */
    internal val appliedStyles: MutableSet<CharacterStyle> = mutableSetOf()

    override var colorScheme: SyntaxColorScheme = getDefaultColorScheme()

    override fun highlight(spannable: Spannable): List<CharacterStyle> {
        clearAppliedStyles(spannable)

        val addedStyles = super.highlight(spannable)

        // remember what styles were applied
        appliedStyles.addAll(addedStyles)

        return addedStyles
    }

    /**
     * Clear any style modifications the syntax highlighter may have made to the [target]
     *
     * @param target the target [Spannable] to clear
     */
    fun clearAppliedStyles(target: Spannable) {
        appliedStyles.forEach {
            target.removeSpan(it)
        }

        appliedStyles.clear()
    }

}