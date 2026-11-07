package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.language.json.rule.CommentRule
import de.markusressel.kodehighlighter.language.json.rule.KeywordRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.PropertyRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class JsonRuleBookTest {

    private val ruleBook = JsonRuleBook()

    @Test
    fun `property names and strings are told apart`() {
        assertHighlighting(
            ruleBook,
            "{\"a\":\"b:\",\"c\" : \"d\", \"e\\\"f\": 1}",
            PropertyRule to listOf("\"a\"", "\"c\"", "\"e\\\"f\""),
            StringRule to listOf("\"b:\"", "\"d\""),
            NumericRule to listOf("1"),
        )
    }

    @Test
    fun `negative numbers keep their sign`() {
        assertHighlighting(
            ruleBook,
            "[-1, -2.5e3, 0, 1E5]",
            NumericRule to listOf("-1", "-2.5e3", "0", "1E5"),
        )
    }

    @Test
    fun `numbers and keywords inside of strings are ignored`() {
        assertHighlighting(
            ruleBook,
            "{\"v1 2\": \"10 true\"}",
            PropertyRule to listOf("\"v1 2\""),
            StringRule to listOf("\"10 true\""),
        )
    }

    @Test
    fun `keywords need word boundaries`() {
        assertHighlighting(ruleBook, "true nullable false", KeywordRule to listOf("true", "false"))
    }

    @Test
    fun `comments are supported`() {
        assertHighlighting(
            ruleBook,
            "// a \"b\"\n/* 1 */ 2",
            CommentRule to listOf("// a \"b\"", "/* 1 */"),
            NumericRule to listOf("2"),
        )
    }
}
