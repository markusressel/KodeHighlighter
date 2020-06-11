package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.support.annotation.RawRes
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.widget.TextView
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.util.EditTextSyntaxHighlighter
import de.markusressel.kodehighlighter.language.json.JsonSyntaxHighlighter
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.python.PythonSyntaxHighlighter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViewSamples()
        initEditTextSample()
    }

    private fun initTextViewSamples() {
        val markdownText = readResourceFileAsText(R.raw.markdown_sample)
        val markdownHighlighter = MarkdownSyntaxHighlighter()
        highlightInCoroutine(markdownText, markdownHighlighter, markdownLight)

        markdownHighlighter.colorScheme = DarkBackgroundColorScheme()
        highlightInCoroutine(markdownText, markdownHighlighter, markdownDark)

        val pythonText = readResourceFileAsText(R.raw.python_example)
        val pythonSyntaxHighlighter = PythonSyntaxHighlighter()
        highlightInCoroutine(pythonText, pythonSyntaxHighlighter, pythonDark)

        val jsonText = readResourceFileAsText(R.raw.json_example)
        val jsonSyntaxHighlighter = JsonSyntaxHighlighter()
        jsonSyntaxHighlighter.colorScheme = de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme()
        highlightInCoroutine(jsonText, jsonSyntaxHighlighter, jsonDark)
    }

    /**
     * Helper function to highlight something in a coroutine
     */
    private fun highlightInCoroutine(text: String, highlighter: SyntaxHighlighter, target: TextView) {
        CoroutineScope(Dispatchers.Main).launch {
            val spannable = async {
                val spannable = createSpannable(text)
                highlighter.highlight(spannable)
                spannable
            }
            target.text = spannable.await()
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
        CoroutineScope(Dispatchers.Main).launch {
            val editTextSyntaxHighlighter = EditTextSyntaxHighlighter(
                    target = editTextMarkdownDark,
                    syntaxHighlighter = MarkdownSyntaxHighlighter())
            editTextSyntaxHighlighter.start()

            val markdown = withContext(Dispatchers.IO) {
                readResourceFileAsText(R.raw.markdown_sample)
            }
            editTextMarkdownDark.setText(markdown)
        }
    }
}
