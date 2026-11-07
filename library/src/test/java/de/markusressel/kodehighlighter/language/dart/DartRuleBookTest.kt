package de.markusressel.kodehighlighter.language.dart

import de.markusressel.kodehighlighter.language.dart.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.dart.rule.CommentRule
import de.markusressel.kodehighlighter.language.dart.rule.KeywordRule
import de.markusressel.kodehighlighter.language.dart.rule.LiteralRule
import de.markusressel.kodehighlighter.language.dart.rule.NumberRule
import de.markusressel.kodehighlighter.language.dart.rule.StringRule
import de.markusressel.kodehighlighter.language.dart.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class DartRuleBookTest {

    private val ruleBook = DartRuleBook()

    @Test
    fun `raw strings ignore escapes`() {
        assertHighlighting(
            ruleBook,
            "r'a\\' 'b\\'c'",
            StringRule to listOf("r'a\\'", "'b\\'c'"),
        )
    }

    @Test
    fun `multi line strings`() {
        assertHighlighting(
            ruleBook,
            "'''a\nclass''' \"\"\"b\"\"\"",
            StringRule to listOf("'''a\nclass'''", "\"\"\"b\"\"\""),
        )
    }

    @Test
    fun `nested comments`() {
        assertHighlighting(
            ruleBook,
            "/* a /* b */ var */ var",
            CommentRule to listOf("/* a /* b */ var */"),
            KeywordRule to listOf("var"),
        )
    }

    @Test
    fun `keywords types and literals`() {
        assertHighlighting(
            ruleBook,
            "class A { final int x = null; } classes",
            KeywordRule to listOf("class", "final"),
            TypeRule to listOf("int"),
            LiteralRule to listOf("null"),
        )
    }

    @Test
    fun `contextual keywords`() {
        assertHighlighting(
            ruleBook,
            "int get x; var get = 1;",
            TypeRule to listOf("int"),
            KeywordRule to listOf("get", "var"),
            NumberRule to listOf("1"),
        )
    }

    @Test
    fun `annotations and numbers`() {
        assertHighlighting(
            ruleBook,
            "@override a1 0xFF 1.5e3",
            AnnotationRule to listOf("@override"),
            NumberRule to listOf("0xFF", "1.5e3"),
        )
    }
}
