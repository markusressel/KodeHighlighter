package de.markusressel.kodehighlighter.core.util

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher

/**
 * TextWatcher with built in support for debouncing fast input
 *
 * @param delayMs debounce delay in milliseconds
 * @param action action to execute on the main thread after the text has stabilized
 */
open class DebouncedTextWatcher(
        var delayMs: Long,
        val action: ((CharSequence?) -> Unit))
    : TextWatcher {

    private val handler = Handler(Looper.getMainLooper())
    private var pendingAction: Runnable? = null

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val text = s?.toString() ?: ""

        cancel()
        val runnable = Runnable { action(text) }
        pendingAction = runnable
        handler.postDelayed(runnable, delayMs)
    }

    /**
     * Cancels the pending action, if any
     */
    open fun cancel() {
        pendingAction?.let(handler::removeCallbacks)
        pendingAction = null
    }

    override fun afterTextChanged(s: Editable?) = Unit
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
}
