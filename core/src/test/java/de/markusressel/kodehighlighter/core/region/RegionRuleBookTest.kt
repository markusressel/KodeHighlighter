package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RegionRuleBookTest {

    private object Code : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> = emptyList()
    }

    private object Keyword : LanguageRule {
        override fun findMatches(text: CharSequence) = RuleHelper.findRegexMatches(text, "\\bif\\b".toRegex())
    }

    private object Bold : LanguageRule {
        override fun findMatches(text: CharSequence) = RuleHelper.findRegexMatches(text, "\\*\\*.+?\\*\\*".toRegex())
    }

    private val ruleBook = object : RegionRuleBook() {
        override val scanner = RegionScanner(listOf(DelimitedRegion(Code, "`")))
        override fun getRules(): List<LanguageRule> = listOf(Bold, Keyword, Code)
    }

    private fun highlight(text: String) = runBlocking { ruleBook.createHighlighting(text) }
        .associate { matches -> matches.rule to matches.matches.map { text.substring(it.startIndex, it.endIndex) } }

    @Test
    fun `matches inside of regions are dropped`() {
        assertEquals(mapOf(Keyword to listOf("if"), Code to listOf("`if`")), highlight("if `if`"))
    }

    @Test
    fun `matches that contain regions are kept`() {
        assertEquals(mapOf(Bold to listOf("**a `b` c**"), Code to listOf("`b`")), highlight("**a `b` c**"))
    }

    @Test
    fun `matches that partly overlap regions are dropped`() {
        assertEquals(mapOf(Code to listOf("`a **b`")), highlight("`a **b` c**"))
        assertEquals(mapOf(Code to listOf("`b** c`")), highlight("**a `b** c`"))
    }

    @Test
    fun `result follows the order of the rules`() {
        val rules = runBlocking { ruleBook.createHighlighting("`a` if **b**") }.map { it.rule }
        assertEquals(listOf(Bold, Keyword, Code), rules)
    }
}
