package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MarkdownTest {

    @Test
    fun heading_test() {
        val syntaxHighlighter = MarkdownSyntaxHighlighter().apply {
            this.colorScheme = DarkBackgroundColorScheme()
        }

        val fillerText = "Test text\n".repeat(50)
        val headers = listOf(
                "# Header",
                "  ## Header",
                "  ### Header",
                "   #### Header",
                " ##### Header",
                "###### Header"
        )

        val text = headers.joinToString(separator = "\n$fillerText\n")

        val highlightEntities = runBlocking {
            syntaxHighlighter.createHighlighting(text)
        }

        // we only expect headers
        Assert.assertEquals(1, highlightEntities.size)

        // test amount of headers
        Assert.assertEquals(headers.size, highlightEntities.first().matches.size)
    }


}