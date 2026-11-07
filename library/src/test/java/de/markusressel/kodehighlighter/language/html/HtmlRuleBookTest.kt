package de.markusressel.kodehighlighter.language.html

import de.markusressel.kodehighlighter.language.html.rule.EmbeddingTagRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeValueRule
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule
import de.markusressel.kodehighlighter.language.xml.rule.DoctypeRule
import de.markusressel.kodehighlighter.language.xml.rule.EntityRule
import de.markusressel.kodehighlighter.language.xml.rule.TagRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test
import de.markusressel.kodehighlighter.language.css.rule.PropertyRule as CssPropertyRule
import de.markusressel.kodehighlighter.language.css.rule.TagSelectorRule as CssTagSelectorRule
import de.markusressel.kodehighlighter.language.javascript.rule.KeywordRule as JsKeywordRule
import de.markusressel.kodehighlighter.language.javascript.rule.NumberRule as JsNumberRule
import de.markusressel.kodehighlighter.language.javascript.rule.StringRule as JsStringRule

class HtmlRuleBookTest {

    private val ruleBook = HtmlRuleBook()

    @Test
    fun `tags attributes and entities`() {
        assertHighlighting(
            ruleBook,
            "<!DOCTYPE html><p class=\"a\">x &amp; y</p>",
            DoctypeRule to listOf("<!DOCTYPE html>"),
            TagRule to listOf(">", "<p", ">", "</p", ">"),
            AttributeRule to listOf("class"),
            AttributeValueRule to listOf("\"a\""),
            EntityRule to listOf("&amp;"),
        )
    }

    @Test
    fun `scripts are javascript`() {
        assertHighlighting(
            ruleBook,
            "<script type=\"module\">let a = 1 < 2, b = \"</p>\";</script><i>",
            TagRule to listOf("<script", "</script", ">", "<i", ">"),
            AttributeRule to listOf("type"),
            AttributeValueRule to listOf("\"module\""),
            EmbeddingTagRule to listOf(">"),
            JsKeywordRule to listOf("let"),
            JsNumberRule to listOf("1", "2"),
            JsStringRule to listOf("\"</p>\""),
        )
    }

    @Test
    fun `styles are css`() {
        assertHighlighting(
            ruleBook,
            "<STYLE>p { color: red; }</STYLE>",
            TagRule to listOf("<STYLE", "</STYLE", ">"),
            EmbeddingTagRule to listOf(">"),
            CssTagSelectorRule to listOf("p"),
            CssPropertyRule to listOf("color"),
        )
    }

    @Test
    fun `self closing scripts and comments embed nothing`() {
        assertHighlighting(
            ruleBook,
            "<script src=\"a.js\"/><!-- <script> --><i>",
            TagRule to listOf("<script", "/>", "<i", ">"),
            AttributeRule to listOf("src"),
            AttributeValueRule to listOf("\"a.js\""),
            CommentRule to listOf("<!-- <script> -->"),
        )
    }

    @Test
    fun `unclosed script ends with the text`() {
        assertHighlighting(
            ruleBook,
            "<script>let a",
            TagRule to listOf("<script"),
            EmbeddingTagRule to listOf(">"),
            JsKeywordRule to listOf("let"),
        )
    }
}
