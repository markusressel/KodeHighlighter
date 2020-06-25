package de.markusressel.kodehighlighter.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import kotlinx.coroutines.*

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
            statefulSyntaxHighlighter = StatefulSpannableHighlightingManager(field, field.defaultColorScheme)
        }

    private var statefulSyntaxHighlighter = StatefulSpannableHighlightingManager(syntaxHighlighter, syntaxHighlighter.defaultColorScheme)
        set(value) {
            clearAppliedStyles()
            field = value
            refreshHighlighting()
        }

    private var highlightingJob: Job? = null

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

    private val debouncedTextWatcher = DebouncedTextWatcher(
            delayMs = 100,
            action = {
                refreshHighlighting()
            })

    private val realtimeTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            highlightingJob?.cancel("Text has changed")
            highlightingJob = null
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    /**
     * (Re-)Highlight the content of the [target]
     */
    open fun refreshHighlighting() {
        highlightingJob?.cancel("Requested new highlighting")
        highlightingJob = null
        if (editable == null) {
            return
        }

        highlightingJob = CoroutineScope(Dispatchers.Main).launch {
            val currentText = editable.toString()
            val highlightEntities = withContext(Dispatchers.Default) {
                statefulSyntaxHighlighter.createHighlighting(currentText)
            }
            clearAppliedStyles()
            try {
                statefulSyntaxHighlighter.highlight(editable, highlightEntities)
            } catch (e: IndexOutOfBoundsException) {
                // text has changed while we were trying to apply styles to it
                // the next highlighting run will fix this
            }
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
        target.addTextChangedListener(debouncedTextWatcher)
        target.addTextChangedListener(realtimeTextWatcher)
    }

    /**
     * Stop continuous highlighting
     */
    open fun cancel() {
        target.removeTextChangedListener(realtimeTextWatcher)
        target.removeTextChangedListener(debouncedTextWatcher)
    }
}