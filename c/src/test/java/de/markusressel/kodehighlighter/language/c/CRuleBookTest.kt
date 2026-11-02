package de.markusressel.kodehighlighter.language.c

import de.markusressel.kodehighlighter.language.c.rule.CommentRule
import de.markusressel.kodehighlighter.language.c.rule.KeywordRule
import de.markusressel.kodehighlighter.language.c.rule.LiteralRule
import de.markusressel.kodehighlighter.language.c.rule.NumberRule
import de.markusressel.kodehighlighter.language.c.rule.PreprocessorRule
import de.markusressel.kodehighlighter.language.c.rule.StringRule
import de.markusressel.kodehighlighter.language.c.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class CRuleBookTest {

    private val ruleBook = CRuleBook()

    @Test
    fun `strings and comments hide keywords`() {
        assertHighlighting(
            ruleBook,
            "\"if //\" /* for */ // while",
            StringRule to listOf("\"if //\""),
            CommentRule to listOf("/* for */", "// while"),
        )
    }

    @Test
    fun `keywords types and literals`() {
        assertHighlighting(
            ruleBook,
            "if (mint) int[] x = NULL; return;",
            KeywordRule to listOf("if", "return"),
            TypeRule to listOf("int"),
            LiteralRule to listOf("NULL"),
        )
    }

    @Test
    fun `preprocessor`() {
        assertHighlighting(
            ruleBook,
            "#include <stdio.h>\n  # define A\nx #y",
            PreprocessorRule to listOf("#include <stdio.h>", "  # define"),
        )
    }

    @Test
    fun `include with quotes is a string`() {
        assertHighlighting(
            ruleBook,
            "#include \"a.h\"",
            PreprocessorRule to listOf("#include"),
            StringRule to listOf("\"a.h\""),
        )
    }

    @Test
    fun `numbers`() {
        assertHighlighting(
            ruleBook,
            "a1 0xFFu 1.5f 10UL .5 0b11 1e3",
            NumberRule to listOf("0xFFu", "1.5f", "10UL", ".5", "0b11", "1e3"),
        )
    }

    @Test
    fun `chars`() {
        assertHighlighting(
            ruleBook,
            "'a' '\\n' '\"'",
            StringRule to listOf("'a'", "'\\n'", "'\"'"),
        )
    }
}
