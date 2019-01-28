package de.markusressel.kodehighlighter.core

import android.widget.EditText

/**
 * Convenience class for using a [SyntaxHighlighter] in an [EditText]
 *
 * Note: If you need to highlight multiple [EditText] objects at the same time
 * be sure to also create one [EditTextSyntaxHighlighter] instance for each [EditText].
 * Otherwise applied styles might not be cleared properly when refreshing highlighting
 * of an already highlighted [EditText].
 */
open class EditTextSyntaxHighlighter(
        /**
         * The [SyntaxHighlighter] to use
         */
        syntaxHighlighter: SyntaxHighlighter,
        /**
         * The target [EditText] to apply syntax highlighting to
         */
        target: EditText)
    : StatefulSyntaxHighlighter(syntaxHighlighter) {

    /**
     * The [Editable] to work with
     */
    val editable
        get() = target.text

    /**
     * The target [EditText] syntax highlighting is applied to
     */
    var target: EditText = target
        set(value) {
            clearAppliedStyles()
            field = value
            refreshHighlighting()
        }

    /**
     * (Re-)Highlight the content of the [target]
     */
    open fun refreshHighlighting() {
        editable?.let { clearAppliedStyles(it) }
        highlight(editable)
    }

    /**
     * Clear all currently applied styles
     */
    fun clearAppliedStyles() {
        clearAppliedStyles(editable)
    }

}