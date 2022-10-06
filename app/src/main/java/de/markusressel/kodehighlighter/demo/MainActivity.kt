package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.text.SpannableString
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import de.markusressel.kodehighlighter.core.util.AnnotatedStringHighlighter
import de.markusressel.kodehighlighter.core.util.EditTextHighlighter
import de.markusressel.kodehighlighter.core.util.SpannableHighlighter
import de.markusressel.kodehighlighter.demo.databinding.ActivityMainBinding
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorSchemeWithSpanStyle
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initComposeSamples()
        initTextViewSamples()
        initEditTextSample()
    }

    private fun initComposeSamples() {
        binding.composeView.setContent {
            MaterialTheme {
                val composeHighlighter by remember {
                    mutableStateOf(
                        AnnotatedStringHighlighter(
                            languageRuleBook = MarkdownRuleBook(),
                            colorScheme = DarkBackgroundColorSchemeWithSpanStyle()
                        )
                    )
                }

                var text by remember {
                    mutableStateOf(readResourceFileAsText(R.raw.markdown_sample))
                }
                var annotatedText by remember {
                    mutableStateOf(AnnotatedString(text = ""))
                }

                LaunchedEffect(text) {
                    annotatedText = composeHighlighter.highlight(AnnotatedString(text = text))
                }

                Text(annotatedText)
            }
        }
    }

    private fun initTextViewSamples() {
        val markdownText = readResourceFileAsText(R.raw.markdown_sample)
        val markdownRuleBook = MarkdownRuleBook()
        val markdownHighlighter =
            SpannableHighlighter(markdownRuleBook, DarkBackgroundColorScheme())

        // TODO: currently there is no light theme
        highlightInCoroutine(markdownText, markdownHighlighter, binding.markdownLight)
        highlightInCoroutine(markdownText, markdownHighlighter, binding.markdownDark)

        val pythonText = readResourceFileAsText(R.raw.python_example)
        val pythonRuleBook = PythonRuleBook()
        val pythonHighlighter = SpannableHighlighter(pythonRuleBook, DarkBackgroundColorScheme())
        highlightInCoroutine(pythonText, pythonHighlighter, binding.pythonDark)

        val jsonText = readResourceFileAsText(R.raw.json_example)
        val jsonRuleBook = JsonRuleBook()
        val jsonHighlighter = SpannableHighlighter(
            jsonRuleBook,
            de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme()
        )
        highlightInCoroutine(jsonText, jsonHighlighter, binding.jsonDark)

        val ocamlText = readResourceFileAsText(R.raw.ocaml_example)
        val ocamlRuleBook = OCamlRuleBook()
        val ocamlHighlighter = SpannableHighlighter(ocamlRuleBook, DarkBackgroundColorScheme())
        highlightInCoroutine(ocamlText, ocamlHighlighter, binding.ocamlDark)
    }

    /**
     * Helper function to highlight something in a coroutine
     */
    private fun highlightInCoroutine(
        text: String,
        highlighter: SpannableHighlighter,
        target: TextView
    ) {
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
            target = binding.editTextMarkdownDark,
            languageRuleBook = MarkdownRuleBook(),
            colorScheme = DarkBackgroundColorScheme()
        )
        editTextHighlighter.start()

        CoroutineScope(Dispatchers.Main).launch {
            val markdown = withContext(Dispatchers.IO) {
                readResourceFileAsText(R.raw.markdown_sample)
            }
            binding.editTextMarkdownDark.setText(markdown)
        }
    }
}
