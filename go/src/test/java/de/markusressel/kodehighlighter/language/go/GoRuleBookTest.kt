package de.markusressel.kodehighlighter.language.go

import de.markusressel.kodehighlighter.language.go.rule.KeywordRule
import de.markusressel.kodehighlighter.language.go.rule.LiteralRule
import de.markusressel.kodehighlighter.language.go.rule.NumberRule
import de.markusressel.kodehighlighter.language.go.rule.StringRule
import de.markusressel.kodehighlighter.language.go.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class GoRuleBookTest {

    private val ruleBook = GoRuleBook()

    @Test
    fun `raw strings ignore escapes and span lines`() {
        assertHighlighting(
            ruleBook,
            "`a\\` \"c\"",
            StringRule to listOf("`a\\`", "\"c\""),
        )
    }

    @Test
    fun `multi line raw string`() {
        assertHighlighting(
            ruleBook,
            "`a\nfunc`",
            StringRule to listOf("`a\nfunc`"),
        )
    }

    @Test
    fun `keywords types and literals`() {
        assertHighlighting(
            ruleBook,
            "func f() error { return nil } funcs",
            KeywordRule to listOf("func", "return"),
            TypeRule to listOf("error"),
            LiteralRule to listOf("nil"),
        )
    }

    @Test
    fun `numbers`() {
        assertHighlighting(
            ruleBook,
            "a1 0xFF 1_000 1.5 0b1 2i .5",
            NumberRule to listOf("0xFF", "1_000", "1.5", "0b1", "2i", ".5"),
        )
    }

    @Test
    fun `runes`() {
        assertHighlighting(
            ruleBook,
            "'a' '\\n' '\\x41'",
            StringRule to listOf("'a'", "'\\n'", "'\\x41'"),
        )
    }
}
