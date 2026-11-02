package de.markusressel.kodehighlighter.language.csharp

import de.markusressel.kodehighlighter.language.csharp.rule.KeywordRule
import de.markusressel.kodehighlighter.language.csharp.rule.LiteralRule
import de.markusressel.kodehighlighter.language.csharp.rule.NumberRule
import de.markusressel.kodehighlighter.language.csharp.rule.PreprocessorRule
import de.markusressel.kodehighlighter.language.csharp.rule.StringRule
import de.markusressel.kodehighlighter.language.csharp.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class CSharpRuleBookTest {

    private val ruleBook = CSharpRuleBook()

    @Test
    fun `verbatim strings use doubled quotes`() {
        assertHighlighting(
            ruleBook,
            "@\"a\"\"b\\\" c",
            StringRule to listOf("@\"a\"\"b\\\""),
        )
    }

    @Test
    fun `interpolated strings`() {
        assertHighlighting(
            ruleBook,
            "\$\"a {b}\" \$@\"c\"",
            StringRule to listOf("\$\"a {b}\"", "\$@\"c\""),
        )
    }

    @Test
    fun `raw strings`() {
        assertHighlighting(
            ruleBook,
            "\"\"\"a \"b\" c\"\"\"",
            StringRule to listOf("\"\"\"a \"b\" c\"\"\""),
        )
    }

    @Test
    fun `keywords and types`() {
        assertHighlighting(
            ruleBook,
            "public string x; async Task y; var async = null;",
            KeywordRule to listOf("public", "async"),
            TypeRule to listOf("string", "var"),
            LiteralRule to listOf("null"),
        )
    }

    @Test
    fun `accessors are contextual`() {
        assertHighlighting(
            ruleBook,
            "{ get; set; } get = 1",
            KeywordRule to listOf("get", "set"),
            NumberRule to listOf("1"),
        )
    }

    @Test
    fun `numbers`() {
        assertHighlighting(
            ruleBook,
            "a1 1_000m 0xFFu 1.5f",
            NumberRule to listOf("1_000m", "0xFFu", "1.5f"),
        )
    }

    @Test
    fun `preprocessor`() {
        assertHighlighting(
            ruleBook,
            "#region a\nx",
            PreprocessorRule to listOf("#region"),
        )
    }
}
