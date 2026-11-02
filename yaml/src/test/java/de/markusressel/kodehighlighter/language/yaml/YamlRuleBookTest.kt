package de.markusressel.kodehighlighter.language.yaml

import de.markusressel.kodehighlighter.language.yaml.rule.AnchorRule
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.DocumentRule
import de.markusressel.kodehighlighter.language.yaml.rule.KeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.ListRule
import de.markusressel.kodehighlighter.language.yaml.rule.LiteralRule
import de.markusressel.kodehighlighter.language.yaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.yaml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.StringRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class YamlRuleBookTest {

    private val ruleBook = YamlRuleBook()

    @Test
    fun `keys and values`() {
        assertHighlighting(
            ruleBook,
            "a: 1\nb c: true\nd: text no\n",
            KeyRule to listOf("a", "b c", "d"),
            NumberRule to listOf("1"),
            LiteralRule to listOf("true"),
        )
    }

    @Test
    fun `lists`() {
        assertHighlighting(
            ruleBook,
            "- a\n- b: 1\n  c: 2\n",
            ListRule to listOf("-", "-"),
            KeyRule to listOf("b", "c"),
            NumberRule to listOf("1", "2"),
        )
    }

    @Test
    fun `comments need a space in front`() {
        assertHighlighting(
            ruleBook,
            "a: b#c # d\n# e: 1",
            KeyRule to listOf("a"),
            CommentRule to listOf("# d", "# e: 1"),
        )
    }

    @Test
    fun `strings hide their content`() {
        assertHighlighting(
            ruleBook,
            "a: \"b: 1 # c\"\nd: 'it''s: e'",
            KeyRule to listOf("a", "d"),
            StringRule to listOf("\"b: 1 # c\"", "'it''s: e'"),
        )
    }

    @Test
    fun `apostrophe in plain text`() {
        assertHighlighting(
            ruleBook,
            "a: it's",
            KeyRule to listOf("a"),
        )
    }

    @Test
    fun `quoted keys`() {
        assertHighlighting(
            ruleBook,
            "\"a b\": 1",
            QuotedKeyRule to listOf("\"a b\""),
            NumberRule to listOf("1"),
        )
    }

    @Test
    fun `documents anchors and tags`() {
        assertHighlighting(
            ruleBook,
            "---\na: &x 1\nb: *x\nc: !!str d",
            DocumentRule to listOf("---"),
            KeyRule to listOf("a", "b", "c"),
            AnchorRule to listOf("&x", "*x", "!!str"),
        )
    }

    @Test
    fun `urls are not keys`() {
        assertHighlighting(
            ruleBook,
            "a: http://b.c",
            KeyRule to listOf("a"),
        )
    }
}
