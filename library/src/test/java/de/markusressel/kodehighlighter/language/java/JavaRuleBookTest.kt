package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.language.java.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.ControlFlowKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.DeclarationKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.LiteralRule
import de.markusressel.kodehighlighter.language.java.rule.ModifierKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.NumberRule
import de.markusressel.kodehighlighter.language.java.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StringRule
import de.markusressel.kodehighlighter.language.java.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class JavaRuleBookTest {

    private val ruleBook = JavaRuleBook()

    @Test
    fun `strings are not comments`() {
        assertHighlighting(
            ruleBook,
            "x = \"hello\"; // c",
            StringRule to listOf("\"hello\""),
            CommentRule to listOf("// c"),
        )
        assertHighlighting(ruleBook, "u = \"http://x\";", StringRule to listOf("\"http://x\""))
    }

    @Test
    fun `char literal with a quote does not start a string`() {
        assertHighlighting(
            ruleBook,
            "q = '\"'; s = \"a\";",
            StringRule to listOf("'\"'", "\"a\""),
        )
    }

    @Test
    fun `keywords inside of strings and comments are ignored`() {
        assertHighlighting(
            ruleBook,
            "\"return int\" /* final class */",
            StringRule to listOf("\"return int\""),
            CommentRule to listOf("/* final class */"),
        )
    }

    @Test
    fun `text blocks span lines`() {
        assertHighlighting(ruleBook, "\"\"\"\n a \"b\"\n\"\"\"", StringRule to listOf("\"\"\"\n a \"b\"\n\"\"\""))
    }

    @Test
    fun `keywords need word boundaries`() {
        assertHighlighting(
            ruleBook,
            "print x; mint y; int[] a; subclass z; Foo.class; return;",
            TypeKeywordRule to listOf("int"),
            ClassKeywordRule to listOf("class"),
            ReturnKeywordRule to listOf("return"),
        )
    }

    @Test
    fun `common keywords are covered`() {
        assertHighlighting(
            ruleBook,
            "abstract if else new null",
            ModifierKeywordRule to listOf("abstract"),
            ControlFlowKeywordRule to listOf("if", "else"),
            DeclarationKeywordRule to listOf("new"),
            LiteralRule to listOf("null"),
        )
    }

    @Test
    fun `contextual keywords need their context`() {
        assertHighlighting(
            ruleBook,
            "record Foo; record = 1; var x; var = 2",
            DeclarationKeywordRule to listOf("record", "var"),
            NumberRule to listOf("1", "2"),
        )
    }

    @Test
    fun `numbers cover all literal forms`() {
        assertHighlighting(
            ruleBook,
            "abc123 x-1 0xFF 1_000L 1.5e-3f .5f 0b1010 2d a1.2",
            NumberRule to listOf("1", "0xFF", "1_000L", "1.5e-3f", ".5f", "0b1010", "2d"),
        )
    }

    @Test
    fun `annotations do not include punctuation or emails`() {
        assertHighlighting(
            ruleBook,
            "foo(@Nullable, @Bar) user@example.com",
            AnnotationRule to listOf("@Nullable", "@Bar"),
        )
    }
}
