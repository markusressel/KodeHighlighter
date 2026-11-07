package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import org.junit.Assert.assertEquals
import org.junit.Test

class RegionScannerTest {

    private open class TestRule : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> = emptyList()
    }

    private object Comment : TestRule()
    private object Str : TestRule()
    private object Key : TestRule()

    private val cStyle = RegionScanner(
        listOf(
            DelimitedRegion(Comment, "//", null),
            DelimitedRegion(Comment, "/*", "*/", multiLine = true),
            DelimitedRegion(Str, "\"\"\"", multiLine = true),
            DelimitedRegion(Str, "\"", escape = '\\'),
        )
    )

    private fun RegionScanner.texts(text: String) =
        scan(text).map { it.rule to text.substring(it.startIndex, it.endIndex) }

    @Test
    fun `comment and string markers inside of each other are ignored`() {
        assertEquals(
            listOf(Str to "\"http://x\"", Comment to "// \"y\"", Comment to "/* \" */"),
            cStyle.texts("a = \"http://x\" // \"y\"\n/* \" */ b"),
        )
    }

    @Test
    fun `escaped delimiters do not close`() {
        assertEquals(listOf(Str to "\"a\\\"b\""), cStyle.texts("\"a\\\"b\" c"))
    }

    @Test
    fun `earlier definitions win`() {
        assertEquals(listOf(Str to "\"\"\"a\n\"b\"\n\"\"\""), cStyle.texts("\"\"\"a\n\"b\"\n\"\"\" c"))
    }

    @Test
    fun `unterminated regions end at line end or text end`() {
        assertEquals(listOf(Str to "\"abc"), cStyle.texts("\"abc\nnext"))
        assertEquals(listOf(Comment to "/* a\nb"), cStyle.texts("/* a\nb"))
        assertEquals(listOf(Comment to "// a"), cStyle.texts("// a\nb"))
    }

    @Test
    fun `escape at the end of the text is safe`() {
        assertEquals(listOf(Str to "\"a\\"), cStyle.texts("\"a\\"))
    }

    @Test
    fun `nested regions need their own end`() {
        val scanner = RegionScanner(listOf(DelimitedRegion(Comment, "(*", "*)", multiLine = true, nested = true)))
        assertEquals(listOf(Comment to "(* a (* b *) c *)"), scanner.texts("(* a (* b *) c *) d"))
        assertEquals(listOf(Comment to "(* a (* b *)"), scanner.texts("(* a (* b *)"))
    }

    @Test
    fun `word boundary is checked`() {
        val scanner = RegionScanner(listOf(DelimitedRegion(Str, "r\"", "\"", wordBoundaryBefore = true)))
        assertEquals(listOf(Str to "r\"a\""), scanner.texts("r\"a\" bar\"b\""))
    }

    @Test
    fun `followedBy selects the rule`() {
        val scanner = RegionScanner(listOf(DelimitedRegion(Str, "\"", escape = '\\', followedBy = mapOf(':' to Key))))
        assertEquals(
            listOf(Key to "\"a\"", Str to "\"b:\"", Key to "\"c\""),
            scanner.texts("{\"a\" : \"b:\", \"c\":1}"),
        )
    }

    @Test
    fun `patterns can look around the current position`() {
        val scanner = RegionScanner(
            listOf(PatternRegion(Str, "`~ ", "(?m)^ {0,3}(`{3,}|~{3,})[^\\n]*\\n[\\s\\S]*?(?:^ {0,3}\\1[ \\t]*$|\\z)".toRegex()))
        )
        assertEquals(
            listOf(Str to "```k\na\n```", Str to " ~~~\nb"),
            scanner.texts("x ```no\n```k\na\n```\ny\n ~~~\nb"),
        )
    }

    @Test
    fun `backreference patterns work`() {
        val scanner = RegionScanner(listOf(PatternRegion(Str, "r", "\\br(#*)\"[\\s\\S]*?\"\\1".toRegex())))
        assertEquals(listOf(Str to "r#\"a\"b\"#"), scanner.texts("r#\"a\"b\"# rust"))
    }

    @Test
    fun `non ascii text and characters are skipped`() {
        assertEquals(listOf(Str to "\"ä\""), cStyle.texts("ü€ \"ä\" ß"))
    }

    @Test
    fun `findMatches reports a single rule`() {
        assertEquals(listOf(RuleMatch(5, 8)), cStyle.findMatches("\"ab\" //c", Comment))
    }

    @Test(timeout = 5_000)
    fun `worst case inputs are fast and do not overflow`() {
        val nestedOpen = RegionScanner(listOf(DelimitedRegion(Comment, "(*", "*)", multiLine = true, nested = true)))
        assertEquals(1, nestedOpen.scan("(*".repeat(500_000)).size)
        assertEquals(1, cStyle.scan("\"" + "\\a".repeat(500_000)).size)
        assertEquals(500_000, cStyle.scan("\"\n".repeat(500_000)).size)
        assertEquals(0, cStyle.scan(" ".repeat(1_000_000)).size)
    }
}
