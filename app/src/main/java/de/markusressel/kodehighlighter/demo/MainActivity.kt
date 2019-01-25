package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val markdown = SpannableString.valueOf("# Title")

        MarkdownSyntaxHighlighter().highlight(markdown)

        textView.text = markdown
    }
}
