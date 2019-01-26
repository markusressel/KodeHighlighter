package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import de.markusressel.kodehighlighter.language.java.JavaSyntaxHighlighter
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViewSample()
        initEditTextSample()
    }

    private fun initTextViewSample() {
        val markdown = SpannableString.valueOf("# Title")
        MarkdownSyntaxHighlighter().highlight(markdown)
        textView.text = markdown
    }

    private fun initEditTextSample() {
        val javaHighlighter = JavaSyntaxHighlighter()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    javaHighlighter.highlight(it)
                }
            }
        })

        val java = "class Test {\n\n  private final String text = \"Hallo!\";\n\n}"
        editText.setText(java)
    }
}
