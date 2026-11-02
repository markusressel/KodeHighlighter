package de.markusressel.kodehighlighter.demo

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.markusressel.kodehighlighter.core.colorscheme.GitHubDarkTheme
import de.markusressel.kodehighlighter.core.util.SpannableHighlighter
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TokenThemeSpannableTest {

    @Test
    fun appliesAllStylesToEveryMatch() {
        val text = SpannableStringBuilder("# a\n# b\n")
        val highlighter = SpannableHighlighter(MarkdownRuleBook(), GitHubDarkTheme.characterStyleColorScheme)

        runBlocking { highlighter.highlight(text) }

        // a span can only be attached once, so a shared instance would leave a single one
        val colors = text.getSpans(0, text.length, ForegroundColorSpan::class.java)
        val styles = text.getSpans(0, text.length, StyleSpan::class.java)
        assertEquals(2, colors.size)
        assertEquals(0xFF1F6FEB.toInt(), colors.first().foregroundColor)
        assertEquals(2, styles.size)
        assertEquals(Typeface.BOLD, styles.first().style)
    }
}
