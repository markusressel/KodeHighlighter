package de.markusressel.kodehighlighter.language.properties

import de.markusressel.kodehighlighter.language.properties.rule.CommentRule
import de.markusressel.kodehighlighter.language.properties.rule.KeyRule
import de.markusressel.kodehighlighter.language.properties.rule.ValueRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class PropertiesRuleBookTest {

    private val ruleBook = PropertiesRuleBook()

    @Test
    fun `keys and values`() {
        assertHighlighting(
            ruleBook,
            "a=b\nc.d : e f\n",
            KeyRule to listOf("a", "c.d"),
            ValueRule to listOf("b", "e f"),
        )
    }

    @Test
    fun `comments`() {
        assertHighlighting(
            ruleBook,
            "# a=b\n  ! c:d\n",
            CommentRule to listOf("# a=b", "  ! c:d"),
        )
    }

    @Test
    fun `values can contain separators`() {
        assertHighlighting(
            ruleBook,
            "a=b=c:d",
            KeyRule to listOf("a"),
            ValueRule to listOf("b=c:d"),
        )
    }

    @Test
    fun `empty value`() {
        assertHighlighting(
            ruleBook,
            "a=",
            KeyRule to listOf("a"),
        )
    }
}
