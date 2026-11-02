package de.markusressel.kodehighlighter.testsupport

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

/**
 * Asserts the complete highlighting of a text: every rule of the rule book that matches must be listed,
 * with its matched text in order of appearance. Rules that are not listed must not match anything.
 *
 * @param ruleBook the rule book to test
 * @param text the text to highlight
 * @param expected the expected rules, each with its matched text passages
 */
fun assertHighlighting(
    ruleBook: LanguageRuleBook,
    text: String,
    vararg expected: Pair<LanguageRule, List<String>>,
) {
    val actualMatches = runBlocking { ruleBook.createHighlighting(text) }
        .associate { ruleMatches ->
            ruleName(ruleMatches.rule) to ruleMatches.matches
                .sortedBy { it.startIndex }
                .map { text.substring(it.startIndex, it.endIndex) }
        }
    val expectedMatches = expected.associate { (rule, matches) -> ruleName(rule) to matches }

    assertEquals("Highlighting of:\n$text\n", expectedMatches.toSortedMap(), actualMatches.toSortedMap())
}

private fun ruleName(rule: LanguageRule): String = rule::class.java.name
