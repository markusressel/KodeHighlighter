package de.markusressel.kodehighlighter.core.rule

import org.junit.Assert.assertEquals
import org.junit.Test

class RuleHelperTest {

    private fun ranges(text: String, regex: Regex) =
        RuleHelper.findRegexMatches(text, regex).map { it.startIndex to it.endIndex }

    @Test
    fun `finds the same matches as findAll`() {
        val cases = listOf(
            "*a* **b** *c*" to "(?<!\\*)\\*(?!\\*).+?\\*(?!\\*)".toRegex(),
            "# one\ntext\n## two\n" to "^\\s{0,3}#{1,6} .+".toRegex(RegexOption.MULTILINE),
            "abc" to "x*".toRegex(),
            "" to "x*".toRegex(),
            "no match" to "z+".toRegex(),
        )
        cases.forEach { (text, regex) ->
            val expected = regex.findAll(text).map { it.range.first to it.range.last + 1 }.toList()
            assertEquals(expected, ranges(text, regex))
        }
    }

    @Test
    fun `group matches return the range of the group`() {
        val matches = RuleHelper.findRegexGroupMatches("a=b c d=e", "(\\w)=(\\w)|(c)".toRegex(), 2)
        assertEquals(listOf(RuleMatch(2, 3), RuleMatch(8, 9)), matches)
    }
}
