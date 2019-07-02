package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MarkdownTest {

    @Test
    fun heading_test() {
        val syntaxHighlighter = MarkdownSyntaxHighlighter().apply {
            colorScheme = DarkBackgroundColorScheme()
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

        // we only expect a single role to trigger
        Assert.assertEquals(1, highlightEntities.size)
        Assert.assertEquals(headers.size, highlightEntities.first().matches.size)
    }

    @Test
    fun italic_test() {
        val syntaxHighlighter = MarkdownSyntaxHighlighter().apply {
            colorScheme = DarkBackgroundColorScheme()
        }

        val fillerText = "Test text\n".repeat(50)
        val strikes = listOf(
                "*italic this text*",
                "*italic\n this\n text*"
        )

        val text = strikes.joinToString(prefix = "Start ", separator = "\n$fillerText\n", postfix = " End!")

        val highlightEntities = runBlocking {
            syntaxHighlighter.createHighlighting(text)
        }

        Assert.assertEquals(1, highlightEntities.size)
        Assert.assertEquals(strikes.size, highlightEntities.first().matches.size)
    }

    @Test
    fun strike_test() {
        val syntaxHighlighter = MarkdownSyntaxHighlighter().apply {
            colorScheme = DarkBackgroundColorScheme()
        }

        val fillerText = "Test text\n".repeat(50)
        val strikes = listOf(
                "~~strike this text~~",
                "~~strike\n this\n text~~"
        )

        val text = strikes.joinToString(prefix = "Start ", separator = "\n$fillerText\n", postfix = " End!")

        val highlightEntities = runBlocking {
            syntaxHighlighter.createHighlighting(text)
        }

        Assert.assertEquals(1, highlightEntities.size)
        Assert.assertEquals(strikes.size, highlightEntities.first().matches.size)
    }


}