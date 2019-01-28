package de.markusressel.kodehighlighter.core

import android.widget.EditText

/**
 * Convenience class for using a [SyntaxHighlighter] in an [EditText]
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

}