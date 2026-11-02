package de.markusressel.kodehighlighter.language.swift

import de.markusressel.kodehighlighter.language.swift.rule.AttributeRule
import de.markusressel.kodehighlighter.language.swift.rule.CommentRule
import de.markusressel.kodehighlighter.language.swift.rule.DirectiveRule
import de.markusressel.kodehighlighter.language.swift.rule.KeywordRule
import de.markusressel.kodehighlighter.language.swift.rule.LiteralRule
import de.markusressel.kodehighlighter.language.swift.rule.NumberRule
import de.markusressel.kodehighlighter.language.swift.rule.StringRule
import de.markusressel.kodehighlighter.language.swift.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class SwiftRuleBookTest {

    private val ruleBook = SwiftRuleBook()

    @Test
    fun `raw and multi line strings`() {
        assertHighlighting(
            ruleBook,
            "#\"a\"b\"# \"\"\"c\nfunc\"\"\"",
            StringRule to listOf("#\"a\"b\"#", "\"\"\"c\nfunc\"\"\""),
        )
    }

    @Test
    fun `nested comments`() {
        assertHighlighting(
            ruleBook,
            "/* a /* b */ let */ let",
            CommentRule to listOf("/* a /* b */ let */"),
            KeywordRule to listOf("let"),
        )
    }

    @Test
    fun `keywords and types`() {
        assertHighlighting(
            ruleBook,
            "func f() -> Int { guard x else { return nil } } funcs",
            KeywordRule to listOf("func", "guard", "else", "return"),
            TypeRule to listOf("Int"),
            LiteralRule to listOf("nil"),
        )
    }

    @Test
    fun `open is contextual`() {
        assertHighlighting(
            ruleBook,
            "open class A; let open = 1",
            KeywordRule to listOf("open", "class", "let"),
            NumberRule to listOf("1"),
        )
    }

    @Test
    fun `attributes and directives`() {
        assertHighlighting(
            ruleBook,
            "@MainActor #available a@b.c",
            AttributeRule to listOf("@MainActor"),
            DirectiveRule to listOf("#available"),
        )
    }

    @Test
    fun `numbers`() {
        assertHighlighting(
            ruleBook,
            "a1 0xFF 1_000 1.5 0b1 0o7",
            NumberRule to listOf("0xFF", "1_000", "1.5", "0b1", "0o7"),
        )
    }
}
