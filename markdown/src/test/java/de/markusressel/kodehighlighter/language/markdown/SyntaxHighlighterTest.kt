package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MarkdownTest {

    @Test
    fun test1() {
        val syntaxHighlighter = MarkdownSyntaxHighlighter().apply {
            this.colorScheme = DarkBackgroundColorScheme()
        }

        val text = "# Header"
        val highlightEntities = runBlocking {
            syntaxHighlighter.createHighlighting(text)
        }
        Assert.assertEquals(1, highlightEntities.size)
    }


}