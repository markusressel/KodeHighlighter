package de.markusressel.kodehighlighter.language.cpp

import de.markusressel.kodehighlighter.language.cpp.rule.CommentRule
import de.markusressel.kodehighlighter.language.cpp.rule.KeywordRule
import de.markusressel.kodehighlighter.language.cpp.rule.NumberRule
import de.markusressel.kodehighlighter.language.cpp.rule.StringRule
import de.markusressel.kodehighlighter.language.cpp.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class CppRuleBookTest {

    private val ruleBook = CppRuleBook()

    @Test
    fun `raw strings`() {
        assertHighlighting(
            ruleBook,
            "R\"x(a\")b)x\" u8R\"(c)\" Bar\"d\"",
            StringRule to listOf("R\"x(a\")b)x\"", "u8R\"(c)\"", "\"d\""),
        )
    }

    @Test
    fun `keywords`() {
        assertHighlighting(
            ruleBook,
            "template <typename T> class A final { virtual; };",
            KeywordRule to listOf("template", "typename", "class", "final", "virtual"),
        )
    }

    @Test
    fun `override is contextual`() {
        assertHighlighting(
            ruleBook,
            "int override = 1; void f() override;",
            TypeRule to listOf("int", "void"),
            NumberRule to listOf("1"),
            KeywordRule to listOf("override"),
        )
    }

    @Test
    fun `digit separators are not chars`() {
        assertHighlighting(
            ruleBook,
            "1'000'000 'a'",
            NumberRule to listOf("1'000'000"),
            StringRule to listOf("'a'"),
        )
    }

    @Test
    fun `strings hide keywords`() {
        assertHighlighting(
            ruleBook,
            "\"class\" // new",
            StringRule to listOf("\"class\""),
            CommentRule to listOf("// new"),
        )
    }
}
