package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class EmbeddingTest {

    private object Block : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> = emptyList()
    }

    private object Keyword : LanguageRule {
        override fun findMatches(text: CharSequence) = RuleHelper.findRegexMatches(text, "\\bif\\b".toRegex())
    }

    private object Number : LanguageRule {
        override fun findMatches(text: CharSequence) = RuleHelper.findRegexMatches(text, "\\d+".toRegex())
    }

    /** a language that has numbers and embeds itself or the [inner] language in `<name>…</name>` blocks */
    private inner class Outer(private val inner: () -> LanguageRuleBook?) : RegionRuleBook() {
        override val scanner = RegionScanner(
            listOf(
                PatternRegion(
                    Block,
                    "<",
                    "<(\\w*)>([\\s\\S]*?)</\\1>".toRegex(),
                    Embedding(contentGroup = 2) { groups -> if (groups[1] == "self") this else inner() },
                ),
            ),
        )

        override fun getRules(): List<LanguageRule> = listOf(Number, Block)
    }

    private val keywords = object : LanguageRuleBook {
        override fun getRules(): List<LanguageRule> = listOf(Keyword, Number)
    }

    private fun highlight(ruleBook: LanguageRuleBook, text: String) = runBlocking { ruleBook.createHighlighting(text) }
        .associate { matches -> matches.rule to matches.matches.map { text.substring(it.startIndex, it.endIndex) } }

    @Test
    fun `the content is highlighted by the embedded rule book and the rest keeps the rule of the region`() {
        assertEquals(
            mapOf(Number to listOf("1", "2", "3"), Block to listOf("<k>", "</k>"), Keyword to listOf("if")),
            highlight(Outer { keywords }, "1 <k>if 2</k> 3"),
        )
    }

    @Test
    fun `without a rule book the whole region gets its rule`() {
        assertEquals(
            mapOf(Number to listOf("1"), Block to listOf("<k>if 2</k>")),
            highlight(Outer { null }, "1 <k>if 2</k>"),
        )
    }

    @Test
    fun `embeddings nest`() {
        val outer = Outer { keywords }
        assertEquals(
            mapOf(Number to listOf("1", "2"), Block to listOf("<self>", "<k>", "</k>", "</self>"), Keyword to listOf("if")),
            highlight(outer, "<self>1 <k>if 2</k></self>"),
        )
    }

    @Test
    fun `an empty content`() {
        assertEquals(mapOf(Block to listOf("<k>", "</k>")), highlight(Outer { keywords }, "<k></k>"))
    }

    @Test
    fun `unchanged content is highlighted once`() {
        var runs = 0
        val counting = object : LanguageRuleBook {
            override fun getRules(): List<LanguageRule> = listOf(Keyword)
            override suspend fun createHighlighting(charSequence: CharSequence) =
                super.createHighlighting(charSequence).also { runs++ }
        }
        val outer = Outer { counting }
        highlight(outer, "1 <k>if</k> 2 <k>if x</k>")
        assertEquals(2, runs)
        // the text around the blocks changed, the blocks did not
        assertEquals(
            mapOf(Number to listOf("3", "4"), Block to listOf("<k>", "</k>", "<k>", "</k>"), Keyword to listOf("if", "if")),
            highlight(outer, "3 <k>if</k> 4 <k>if x</k>"),
        )
        assertEquals(2, runs)
        highlight(outer, "3 <k>if</k> 4 <k>if y</k>")
        assertEquals(3, runs)
    }
}
