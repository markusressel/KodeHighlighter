package de.markusressel.kodehighlighter.core.util

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * TextWatcher with built in support for debouncing fast input
 *
 * @param delayMs debounce delay in milliseconds
 * @param action action to execute after the text has stabilized
 */
open class DebouncedTextWatcher(
        val delayMs: Long,
        val action: ((CharSequence?) -> Unit))
    : TextWatcher {

    private var previousText: CharSequence = ""

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val text = s?.toString() ?: ""
        if (text == previousText)
            return

        previousText = text

        CoroutineScope(Dispatchers.Main).launch {
            delay(delayMs)  //debounce timeOut
            if (text != previousText)
                return@launch

            action(text)
        }
    }

    override fun afterTextChanged(s: Editable?) = Unit
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
}
