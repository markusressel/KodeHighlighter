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
     * The target [EditText] syntax highlighting is applied to
     */
    var target: EditText = target
        set(value) {
            clearAppliedStyles(field.text)
            field = value
            refreshHighlighting()
        }

    /**
     * (Re-)Highlight the content of the [target]
     */
    open fun refreshHighlighting() {
        target.text?.let { clearAppliedStyles(it) }
        highlight(target.text)
    }

    /**
     * Clear all currently applied styles
     */
    fun clearAppliedStyles() {
        clearAppliedStyles(target.text)
    }

}