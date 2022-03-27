package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.text.SpannableString
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import de.markusressel.kodehighlighter.core.util.EditTextHighlighter
import de.markusressel.kodehighlighter.core.util.SpannableHighlighter
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViewSamples()
        initEditTextSample()
    }

    private fun initTextViewSamples() {
        val markdownText = readResourceFileAsText(R.raw.markdown_sample)
        val markdownRuleBook = MarkdownRuleBook()
        val markdownHighlighter = SpannableHighlighter(markdownRuleBook, DarkBackgroundColorScheme())

        // TODO: currently there is no light theme
        highlightInCoroutine(markdownText, markdownHighlighter, markdownLight)
        highlightInCoroutine(markdownText, markdownHighlighter, markdownDark)

        val pythonText = readResourceFileAsText(R.raw.python_example)
        val pythonRuleBook = PythonRuleBook()
        val pythonHighlighter = SpannableHighlighter(pythonRuleBook)
        highlightInCoroutine(pythonText, pythonHighlighter, pythonDark)

        val jsonText = readResourceFileAsText(R.raw.json_example)
        val jsonRuleBook = JsonRuleBook()
        val jsonHighlighter = SpannableHighlighter(jsonRuleBook, de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme())
        highlightInCoroutine(jsonText, jsonHighlighter, jsonDark)

        val ocamlText = readResourceFileAsText(R.raw.ocaml_example)
        val ocamlRuleBook = OCamlRuleBook()
        val ocamlHighlighter = SpannableHighlighter(ocamlRuleBook)
        highlightInCoroutine(ocamlText, ocamlHighlighter, ocamlDark)
    }

    /**
     * Helper function to highlight something in a coroutine
     */
    private fun highlightInCoroutine(text: String, highlighter: SpannableHighlighter, target: TextView) {
        CoroutineScope(Dispatchers.Main).launch {
            val spannable = withContext(Dispatchers.Default) {
                val spannable = createSpannable(text)
                highlighter.highlight(spannable)
                spannable
            }
            target.text = spannable
        }
    }

    private fun readResourceFileAsText(@RawRes resourceId: Int): String {
        return resources.openRawResource(resourceId).bufferedReader().readText()
    }

    /**
     * Creates a Spannable from a text
     */
    private fun createSpannable(text: String): SpannableString {
        return SpannableString.valueOf(text)
    }

    private fun initEditTextSample() {
        val editTextHighlighter = EditTextHighlighter(
                target = editTextMarkdownDark,
                languageRuleBook = MarkdownRuleBook())
        editTextHighlighter.start()

        CoroutineScope(Dispatchers.Main).launch {
            val markdown = withContext(Dispatchers.IO) {
                readResourceFileAsText(R.raw.markdown_sample)
            }
            editTextMarkdownDark.setText(markdown)
        }
    }
}
