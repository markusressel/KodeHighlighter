package de.markusressel.kodehighlighter.language.generic

import de.markusressel.kodehighlighter.language.generic.rule.CommentRule
import de.markusressel.kodehighlighter.language.generic.rule.FunctionRule
import de.markusressel.kodehighlighter.language.generic.rule.KeywordRule
import de.markusressel.kodehighlighter.language.generic.rule.LiteralRule
import de.markusressel.kodehighlighter.language.generic.rule.NumberRule
import de.markusressel.kodehighlighter.language.generic.rule.StringRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Assert.assertNull
import org.junit.Test

class GenericRuleBookTest {

    private val ruleBook = GenericRuleBook()

    @Test
    fun `common syntax`() {
        assertHighlighting(
            ruleBook,
            "function f(a) { if (a) return 0x1F + 2.5; } // c\n# d\nx = 'y' /* z */ true",
            KeywordRule to listOf("function", "if", "return"),
            FunctionRule to listOf("f"),
            NumberRule to listOf("0x1F", "2.5"),
            CommentRule to listOf("// c", "# d", "/* z */"),
            StringRule to listOf("'y'"),
            LiteralRule to listOf("true"),
        )
    }

    @Test
    fun `has no language`() {
        assertNull(ruleBook.language)
    }
}
