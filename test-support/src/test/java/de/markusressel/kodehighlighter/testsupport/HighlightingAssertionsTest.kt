package de.markusressel.kodehighlighter.testsupport

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import org.junit.Assert.assertThrows
import org.junit.Test

class HighlightingAssertionsTest {

    private object WordRule : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> =
            RuleHelper.findRegexMatches(text, "[a-z]+".toRegex())
    }

    private object DigitRule : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> =
            RuleHelper.findRegexMatches(text, "\\d+".toRegex())
    }

    private val ruleBook = object : LanguageRuleBook {
        override fun getRules(): List<LanguageRule> = listOf(WordRule, DigitRule)
    }

    @Test
    fun `passes when all matches are listed`() {
        assertHighlighting(
            ruleBook,
            "ab 12 cd",
            WordRule to listOf("ab", "cd"),
            DigitRule to listOf("12"),
        )
    }

    @Test
    fun `passes for a text without matches`() {
        assertHighlighting(ruleBook, "--")
    }

    @Test
    fun `fails when a matching rule is not listed`() {
        assertThrows(AssertionError::class.java) {
            assertHighlighting(ruleBook, "ab 12", WordRule to listOf("ab"))
        }
    }

    @Test
    fun `fails when the matched text differs`() {
        assertThrows(AssertionError::class.java) {
            assertHighlighting(ruleBook, "ab", WordRule to listOf("abc"))
        }
    }
}
