package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.language.markdown.rule.AutolinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.BlockquoteRule
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.TaskRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class MarkdownRuleBookTest {

    // code blocks without their language, CodeBlockLanguageTest has those
    private val ruleBook = MarkdownRuleBook { null }

    @Test
    fun `fenced code hides its content`() {
        assertHighlighting(
            ruleBook,
            "t\n```kt\n# a `b` **c**\n```\nu\n~~~\nopen",
            CodeBlockRule to listOf("```kt\n# a `b` **c**\n```", "~~~\nopen"),
        )
    }

    @Test
    fun `fence markers must start a line`() {
        assertHighlighting(ruleBook, "a ```b``` c", CodeInlineRule to listOf("```b```"))
    }

    @Test
    fun `inline code hides its content`() {
        assertHighlighting(
            ruleBook,
            "`**x**` **y** ``a`b``",
            CodeInlineRule to listOf("`**x**`", "``a`b``"),
            BoldRule to listOf("**y**"),
        )
    }

    @Test
    fun `bold around inline code is kept`() {
        assertHighlighting(
            ruleBook,
            "**a `b` c**",
            BoldRule to listOf("**a `b` c**"),
            CodeInlineRule to listOf("`b`"),
        )
    }

    @Test
    fun `bold forms`() {
        assertHighlighting(
            ruleBook,
            "**y** a__b__c __z__ ** no **",
            BoldRule to listOf("**y**", "__z__"),
        )
    }

    @Test
    fun `italic forms and arithmetic`() {
        assertHighlighting(
            ruleBook,
            "*c* 2 * 3 * 4 snake_case_x _d_",
            ItalicRule to listOf("*c*", "_d_"),
        )
    }

    @Test
    fun `headings stay on their line`() {
        assertHighlighting(
            ruleBook,
            "text\n\n# H\n  ## J\n#nope",
            HeadingRule to listOf("# H", "  ## J"),
        )
    }

    @Test
    fun `lists and tasks`() {
        assertHighlighting(
            ruleBook,
            "x\n\n  - a\n1. b\n2) c\n- [x] d",
            ListRule to listOf("  -", "1.", "2)", "-"),
            TaskRule to listOf("- [x]"),
        )
    }

    @Test
    fun `blockquote and horizontal rule`() {
        assertHighlighting(
            ruleBook,
            "> a\nb > c\n---\n",
            BlockquoteRule to listOf("> a"),
            HorizontalRuleRule to listOf("---"),
        )
    }

    @Test
    fun `only the innermost brackets are a link`() {
        assertHighlighting(
            ruleBook,
            "[a [b](c) [![d](e)](f)",
            TextLinkRule to listOf("[b](c)"),
            ImageLinkRule to listOf("![d](e)"),
        )
    }

    @Test
    fun `links and images`() {
        assertHighlighting(
            ruleBook,
            "[a](b) ![c](d) see https://e.f/g",
            TextLinkRule to listOf("[a](b)"),
            ImageLinkRule to listOf("![c](d)"),
            AutolinkRule to listOf("https://e.f/g"),
        )
    }
}
