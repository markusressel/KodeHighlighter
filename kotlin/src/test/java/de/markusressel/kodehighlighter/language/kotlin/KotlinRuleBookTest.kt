package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.language.kotlin.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ControlFlowKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.FunctionKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.LiteralRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ModifierKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.NumberRule
import de.markusressel.kodehighlighter.language.kotlin.rule.OpenKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.StringRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VarKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VisibilityKeywordRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class KotlinRuleBookTest {

    private val ruleBook = KotlinRuleBook()

    @Test
    fun `nested comments are one comment`() {
        assertHighlighting(
            ruleBook,
            "/* a /* b */ val c */ fun",
            CommentRule to listOf("/* a /* b */ val c */"),
            FunctionKeywordRule to listOf("fun"),
        )
    }

    @Test
    fun `raw strings ignore escapes`() {
        assertHighlighting(ruleBook, "\"\"\"\\\"\"\" fun", StringRule to listOf("\"\"\"\\\"\"\""), FunctionKeywordRule to listOf("fun"))
    }

    @Test
    fun `quotes at the end of a raw string belong to it`() {
        assertHighlighting(ruleBook, "\"\"\"a \"b\"\"\"\" fun", StringRule to listOf("\"\"\"a \"b\"\"\"\""), FunctionKeywordRule to listOf("fun"))
    }

    @Test
    fun `data object`() {
        assertHighlighting(ruleBook, "data object A", ClassKeywordRule to listOf("data object"))
    }

    @Test
    fun `keywords inside of strings are ignored`() {
        assertHighlighting(ruleBook, "\"private fun\" // val", StringRule to listOf("\"private fun\""), CommentRule to listOf("// val"))
    }

    @Test
    fun `keywords need word boundaries`() {
        assertHighlighting(
            ruleBook,
            "interval x; funfun y; val z; fun f",
            VarKeywordRule to listOf("val"),
            FunctionKeywordRule to listOf("fun"),
        )
    }

    @Test
    fun `open is highlighted`() {
        assertHighlighting(
            ruleBook,
            "open fun f",
            OpenKeywordRule to listOf("open"),
            FunctionKeywordRule to listOf("fun"),
        )
    }

    @Test
    fun `modifiers only count in front of a declaration`() {
        assertHighlighting(
            ruleBook,
            "val open = 1; val public = 2; value = 3; suspend fun",
            VarKeywordRule to listOf("val", "val"),
            NumberRule to listOf("1", "2", "3"),
            ModifierKeywordRule to listOf("suspend"),
            FunctionKeywordRule to listOf("fun"),
        )
    }

    @Test
    fun `visibility and data class`() {
        assertHighlighting(
            ruleBook,
            "private data class A",
            VisibilityKeywordRule to listOf("private"),
            ClassKeywordRule to listOf("data class"),
        )
    }

    @Test
    fun `control flow and literals`() {
        assertHighlighting(
            ruleBook,
            "if (a) when; return null",
            ControlFlowKeywordRule to listOf("if", "when"),
            ReturnKeywordRule to listOf("return"),
            LiteralRule to listOf("null"),
        )
    }

    @Test
    fun `numbers cover all literal forms and do not eat member access`() {
        assertHighlighting(
            ruleBook,
            "abc123 x-1 0xFFu 1_000L 1.5f 42uL 1.toString() 0b1 .5",
            NumberRule to listOf("1", "0xFFu", "1_000L", "1.5f", "42uL", "1", "0b1", ".5"),
        )
    }

    @Test
    fun `annotations support use site targets`() {
        assertHighlighting(
            ruleBook,
            "@get:Rule @Foo, user@example.com",
            AnnotationRule to listOf("@get:Rule", "@Foo"),
        )
    }
}
