package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.language.python.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.CommentRule
import de.markusressel.kodehighlighter.language.python.rule.DecoratorRule
import de.markusressel.kodehighlighter.language.python.rule.DefKeyword
import de.markusressel.kodehighlighter.language.python.rule.ExceptRule
import de.markusressel.kodehighlighter.language.python.rule.InternalFunctionsRule
import de.markusressel.kodehighlighter.language.python.rule.NumberRule
import de.markusressel.kodehighlighter.language.python.rule.OtherKeywordsRule
import de.markusressel.kodehighlighter.language.python.rule.SelfKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.StringRule
import de.markusressel.kodehighlighter.language.python.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class PythonRuleBookTest {

    private val ruleBook = PythonRuleBook()

    @Test
    fun `hash inside of a string is not a comment`() {
        assertHighlighting(
            ruleBook,
            "url = \"a#b\"  # real",
            StringRule to listOf("\"a#b\""),
            CommentRule to listOf("# real"),
        )
    }

    @Test
    fun `string prefixes and triple quotes`() {
        assertHighlighting(
            ruleBook,
            "f\"x{y}\" rb'z' \"\"\"d\nif\"\"\"",
            StringRule to listOf("f\"x{y}\"", "rb'z'", "\"\"\"d\nif\"\"\""),
        )
    }

    @Test
    fun `letters in front of a quote are not a prefix unless they are one`() {
        assertHighlighting(ruleBook, "bar\"b\"", StringRule to listOf("\"b\""))
    }

    @Test
    fun `keywords inside of strings are ignored`() {
        assertHighlighting(ruleBook, "'if for'", StringRule to listOf("'if for'"))
    }

    @Test
    fun `keywords need word boundaries`() {
        assertHighlighting(
            ruleBook,
            "myself selfish isTrue None1 subclass self True",
            SelfKeywordRule to listOf("self"),
            TypeKeywordRule to listOf("True"),
        )
    }

    @Test
    fun `all control flow keywords are covered`() {
        assertHighlighting(
            ruleBook,
            "import if else for while try and or async await yield",
            OtherKeywordsRule to listOf("import", "if", "else", "for", "while", "try", "and", "or", "async", "await", "yield"),
        )
    }

    @Test
    fun `except forms`() {
        assertHighlighting(
            ruleBook,
            "except (A, B):\nexcept:",
            ExceptRule to listOf("except", "except"),
        )
    }

    @Test
    fun `class and def`() {
        assertHighlighting(
            ruleBook,
            "class A: def f(x): def",
            ClassKeywordRule to listOf("class"),
            DefKeyword to listOf("def f", "def"),
        )
    }

    @Test
    fun `builtins need a boundary and a call`() {
        assertHighlighting(
            ruleBook,
            "abs(x) myprint(y) a.print(z) print (w) print",
            InternalFunctionsRule to listOf("abs", "print"),
        )
    }

    @Test
    fun `decorators are at the start of a line`() {
        assertHighlighting(
            ruleBook,
            "@a.b\n  @c\nx @ y; x@y",
            DecoratorRule to listOf("@a.b", "  @c"),
        )
    }

    @Test
    fun `numbers cover all literal forms`() {
        assertHighlighting(
            ruleBook,
            "abc123 0xFF 1_000 1.5e3 2j 0b1 .5",
            NumberRule to listOf("0xFF", "1_000", "1.5e3", "2j", "0b1", ".5"),
        )
    }
}
