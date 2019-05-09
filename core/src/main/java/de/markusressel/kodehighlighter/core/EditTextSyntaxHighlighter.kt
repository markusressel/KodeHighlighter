package de.markusressel.kodehighlighter.core

import android.text.Editable
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        target: EditText) {

    /**
     * The [SyntaxHighlighter] to use
     */
    var syntaxHighlighter: SyntaxHighlighter = syntaxHighlighter
        set(value) {
            field = value
            statefulSyntaxHighlighter = StatefulSyntaxHighlighter(value)
        }

    private var statefulSyntaxHighlighter = StatefulSyntaxHighlighter(syntaxHighlighter)
        set(value) {
            clearAppliedStyles()
            field = value
            refreshHighlighting()
        }

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

    private val textWatcher = DebouncedTextWatcher(
            delayMs = 100,
            action = {
                refreshHighlighting()
            })

    /**
     * (Re-)Highlight the content of the [target]
     */
    open fun refreshHighlighting() {
        if (editable == null) {
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val currentText = editable.toString()
            val highlightEntities = withContext(Dispatchers.Default) {
                statefulSyntaxHighlighter.createHighlighting(currentText)
            }
            clearAppliedStyles()
            statefulSyntaxHighlighter.highlight(editable, highlightEntities)
        }
    }

    /**
     * Clear all currently applied styles
     */
    open fun clearAppliedStyles() {
        statefulSyntaxHighlighter.clearAppliedStyles(editable)
    }

    /**
     * Start continuous highlighting
     */
    open fun start() {
        target.addTextChangedListener(textWatcher)
    }

    /**
     * Stop continuous highlighting
     */
    open fun cancel() {
        target.removeTextChangedListener(textWatcher)
    }
}