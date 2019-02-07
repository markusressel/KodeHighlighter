package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.support.annotation.RawRes
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.util.Log
import de.markusressel.kodehighlighter.core.EditTextSyntaxHighlighter
import de.markusressel.kodehighlighter.language.java.JavaSyntaxHighlighter
import de.markusressel.kodehighlighter.language.json.JsonSyntaxHighlighter
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import de.markusressel.kodehighlighter.language.python.PythonSyntaxHighlighter
import de.markusressel.kodehighlighter.language_detection.LanguageClassifier
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViewSamples()
        initEditTextSample()

        val pythonText = readResourceFileAsText(R.raw.python_example)
        val classifier = LanguageClassifier(this)
        val result = classifier.recognizeLanguage(pythonText)
        Log.d("BLA", result.joinToString(separator = "\n"))
    }

    private fun initTextViewSamples() {
        val markdownText = readResourceFileAsText(R.raw.markdown_sample)
        val markdownHighlighter = MarkdownSyntaxHighlighter()
        createSpannable(markdownText).apply {
            markdownHighlighter.highlight(this)
            markdownLight.text = this
        }
        createSpannable(markdownText).apply {
            markdownHighlighter.highlight(this)
            markdownDark.text = this
        }

        val pythonText = readResourceFileAsText(R.raw.python_example)
        createSpannable(pythonText).apply {
            PythonSyntaxHighlighter().highlight(this)
            pythonDark.text = this
        }

        val json = readResourceFileAsText(R.raw.json_example)
        createSpannable(json).apply {
            JsonSyntaxHighlighter().highlight(this)
            jsonDark.text = this
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
        val javaHighlighter = EditTextSyntaxHighlighter(
                target = editTextMarkdownDark,
                syntaxHighlighter = JavaSyntaxHighlighter())

        editTextMarkdownDark.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                javaHighlighter.refreshHighlighting()
            }
        })

        val java = readResourceFileAsText(R.raw.java_sample)
        editTextMarkdownDark.setText(java)
    }
}
