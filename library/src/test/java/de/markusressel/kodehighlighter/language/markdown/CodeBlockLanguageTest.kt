package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.KeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.NumberRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Assert.assertEquals
import org.junit.Test

class CodeBlockLanguageTest {

    private val asked = mutableListOf<String>()
    private val yaml = YamlRuleBook()
    private val ruleBook = MarkdownRuleBook { language ->
        asked += language
        yaml.takeIf { language == "yaml" }
    }

    @Test
    fun `a code block is highlighted in its language`() {
        assertHighlighting(
            ruleBook,
            "# Config\n```yaml\na: 1 # **x**\n```\n**done**",
            HeadingRule to listOf("# Config"),
            CodeBlockRule to listOf("```yaml\n", "```"),
            KeyRule to listOf("a"),
            NumberRule to listOf("1"),
            CommentRule to listOf("# **x**"),
            BoldRule to listOf("**done**"),
        )
    }

    @Test
    fun `an unknown language or none keeps the whole block as code`() {
        assertHighlighting(
            ruleBook,
            "```cobol\na: 1\n```\n```\nb: 2\n```",
            CodeBlockRule to listOf("```cobol\na: 1\n```", "```\nb: 2\n```"),
        )
    }

    @Test
    fun `an unclosed block is highlighted to the end`() {
        assertHighlighting(
            ruleBook,
            "~~~yaml\na: 1\nb: 2",
            CodeBlockRule to listOf("~~~yaml\n"),
            KeyRule to listOf("a", "b"),
            NumberRule to listOf("1", "2"),
        )
    }

    @Test
    fun `each language is asked for once`() {
        repeat(3) { assertHighlighting(ruleBook, "```cobol\nx\n```", CodeBlockRule to listOf("```cobol\nx\n```")) }
        assertEquals(listOf("cobol"), asked)
    }

    @Test
    fun `the language comes from the info string`() {
        assertEquals("kotlin", MarkdownRegions.infoLanguage("kotlin"))
        assertEquals("py", MarkdownRegions.infoLanguage(" py title=\"a.py\" linenums=\"1\""))
        assertEquals("rust", MarkdownRegions.infoLanguage("{.rust .numberLines}"))
        assertEquals("c++", MarkdownRegions.infoLanguage("c++{1,3}"))
        assertEquals(null, MarkdownRegions.infoLanguage(""))
        assertEquals(null, MarkdownRegions.infoLanguage("   "))
        assertEquals(null, MarkdownRegions.infoLanguage(null))
    }

    @Test
    fun `a resolver that knows nothing keeps code blocks as they were`() {
        val plain: LanguageRuleBook = MarkdownRuleBook { null }
        assertHighlighting(plain, "```yaml\na: 1\n```", CodeBlockRule to listOf("```yaml\na: 1\n```"))
    }

    @Test
    fun `by default every language of KodeHighlighter is known`() {
        assertHighlighting(
            MarkdownRuleBook(),
            "```yml\na: 1\n```\n```cobol\nb: 2\n```",
            CodeBlockRule to listOf("```yml\n", "```", "```cobol\nb: 2\n```"),
            KeyRule to listOf("a"),
            NumberRule to listOf("1"),
        )
    }

    @Test
    fun `changing the language of a block highlights it in the new one`() {
        val markdown = MarkdownRuleBook()
        assertHighlighting(markdown, "```yaml\na: 1\n```", CodeBlockRule to listOf("```yaml\n", "```"), KeyRule to listOf("a"), NumberRule to listOf("1"))
        assertHighlighting(markdown, "```text\na: 1\n```", CodeBlockRule to listOf("```text\na: 1\n```"))
        assertHighlighting(markdown, "```yaml\na: 1\n```", CodeBlockRule to listOf("```yaml\n", "```"), KeyRule to listOf("a"), NumberRule to listOf("1"))
    }

    @Test
    fun `deleting the closing fence makes the block reach the end`() {
        val markdown = MarkdownRuleBook()
        assertHighlighting(
            markdown,
            "```yaml\na: 1\n```\n# Title\nb: 2",
            CodeBlockRule to listOf("```yaml\n", "```"),
            KeyRule to listOf("a"),
            NumberRule to listOf("1"),
            HeadingRule to listOf("# Title"),
        )
        assertHighlighting(
            markdown,
            "```yaml\na: 1\n# Title\nb: 2",
            CodeBlockRule to listOf("```yaml\n"),
            KeyRule to listOf("a", "b"),
            NumberRule to listOf("1", "2"),
            CommentRule to listOf("# Title"),
        )
    }
}
