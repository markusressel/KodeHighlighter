package de.markusressel.kodehighlighter.language.toml

import de.markusressel.kodehighlighter.language.toml.rule.CommentRule
import de.markusressel.kodehighlighter.language.toml.rule.DateRule
import de.markusressel.kodehighlighter.language.toml.rule.KeyRule
import de.markusressel.kodehighlighter.language.toml.rule.LiteralRule
import de.markusressel.kodehighlighter.language.toml.rule.NumberRule
import de.markusressel.kodehighlighter.language.toml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.toml.rule.StringRule
import de.markusressel.kodehighlighter.language.toml.rule.TableRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class TomlRuleBookTest {

    private val ruleBook = TomlRuleBook()

    @Test
    fun `tables and keys`() {
        assertHighlighting(
            ruleBook,
            "[a.b]\nc = 1\n[[d]]\ne.f = true",
            TableRule to listOf("[a.b]", "[[d]]"),
            KeyRule to listOf("c", "e.f"),
            NumberRule to listOf("1"),
            LiteralRule to listOf("true"),
        )
    }

    @Test
    fun `strings hide their content`() {
        assertHighlighting(
            ruleBook,
            "a = \"b = 1 # c\" # d\ne = 'f\\'",
            KeyRule to listOf("a", "e"),
            StringRule to listOf("\"b = 1 # c\"", "'f\\'"),
            CommentRule to listOf("# d"),
        )
    }

    @Test
    fun `quoted keys`() {
        assertHighlighting(
            ruleBook,
            "\"a b\" = 'c'",
            QuotedKeyRule to listOf("\"a b\""),
            StringRule to listOf("'c'"),
        )
    }

    @Test
    fun `multi line strings`() {
        assertHighlighting(
            ruleBook,
            "a = \"\"\"b\nc\"\"\"",
            KeyRule to listOf("a"),
            StringRule to listOf("\"\"\"b\nc\"\"\""),
        )
    }

    @Test
    fun `numbers and dates`() {
        assertHighlighting(
            ruleBook,
            "a = [1_000, -2.5e3, 0xFF, inf, 2024-01-02, 2024-01-02T03:04:05Z]",
            KeyRule to listOf("a"),
            NumberRule to listOf("1_000", "-2.5e3", "0xFF", "inf"),
            DateRule to listOf("2024-01-02", "2024-01-02T03:04:05Z"),
        )
    }
}
