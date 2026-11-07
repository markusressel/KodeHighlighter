package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.DocumentRule
import de.markusressel.kodehighlighter.language.yaml.rule.KeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.LiteralRule
import de.markusressel.kodehighlighter.language.yaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.yaml.rule.StringRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test
import de.markusressel.kodehighlighter.language.yaml.rule.ListRule as YamlListRule

class FrontMatterTest {

    private val ruleBook = MarkdownRuleBook()

    @Test
    fun `front matter is highlighted as yaml`() {
        assertHighlighting(
            ruleBook,
            "---\ntitle: \"Home\"\nweight: 2\ndraft: false\ntags:\n  - news\n# a **comment**\n---\n# Heading\n**bold**",
            DocumentRule to listOf("---", "---"),
            KeyRule to listOf("title", "weight", "draft", "tags"),
            StringRule to listOf("\"Home\""),
            NumberRule to listOf("2"),
            LiteralRule to listOf("false"),
            YamlListRule to listOf("  -"),
            CommentRule to listOf("# a **comment**"),
            HeadingRule to listOf("# Heading"),
            BoldRule to listOf("**bold**"),
        )
    }

    @Test
    fun `front matter can end with dots`() {
        assertHighlighting(
            ruleBook,
            "---\na: 1\n...\n- item",
            DocumentRule to listOf("---", "..."),
            KeyRule to listOf("a"),
            NumberRule to listOf("1"),
            ListRule to listOf("-"),
        )
    }

    @Test
    fun `markdown inside front matter is not highlighted`() {
        assertHighlighting(
            ruleBook,
            "---\n# not a heading\n- not: a list\n---\n",
            DocumentRule to listOf("---", "---"),
            CommentRule to listOf("# not a heading"),
            YamlListRule to listOf("-"),
            KeyRule to listOf("not"),
        )
    }

    @Test
    fun `unclosed front matter is a horizontal rule`() {
        assertHighlighting(ruleBook, "---\ntitle: x\n", HorizontalRuleRule to listOf("---"))
    }

    @Test
    fun `front matter only counts at the start`() {
        assertHighlighting(
            ruleBook,
            "text\n\n---\ntitle: x\n---\n",
            HorizontalRuleRule to listOf("---", "---"),
        )
        assertHighlighting(ruleBook, " ---\na: 1\n---", HorizontalRuleRule to listOf(" ---", "---"))
    }

    @Test
    fun `a rule after the front matter stays a rule`() {
        assertHighlighting(
            ruleBook,
            "---\na: b\n---\n\n---\n",
            DocumentRule to listOf("---", "---"),
            KeyRule to listOf("a"),
            HorizontalRuleRule to listOf("---"),
        )
    }

    @Test
    fun `windows line endings and an empty front matter`() {
        assertHighlighting(
            ruleBook,
            "---\r\na: 1\r\n---\r\nx",
            DocumentRule to listOf("---", "---"),
            KeyRule to listOf("a"),
            NumberRule to listOf("1"),
        )
        assertHighlighting(ruleBook, "---\n---\nx", DocumentRule to listOf("---", "---"))
    }
}
