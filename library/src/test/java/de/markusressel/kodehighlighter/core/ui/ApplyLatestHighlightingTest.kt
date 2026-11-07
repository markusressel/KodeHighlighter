package de.markusressel.kodehighlighter.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ApplyLatestHighlightingTest {

    private val style = SpanStyle(Color.Red)

    private fun highlighted(text: String, start: Int, end: Int) = AnnotatedString(
        text = text,
        spanStyles = listOf(AnnotatedString.Range(style, start, end)),
    )

    @Test
    fun `no result yet gives plain text`() {
        val result = applyLatestHighlighting("abc", null)
        assertEquals("abc", result.text)
        assertEquals(emptyList<AnnotatedString.Range<SpanStyle>>(), result.spanStyles)
    }

    @Test
    fun `result for the same text is used as is`() {
        val latest = highlighted("abc", 0, 3)
        assertSame(latest, applyLatestHighlighting("abc", latest))
    }

    private fun assertSpans(text: String, latest: AnnotatedString, vararg expected: Pair<Int, Int>) {
        val result = applyLatestHighlighting(text, latest)
        assertEquals(text, result.text)
        assertEquals(expected.map { AnnotatedString.Range(style, it.first, it.second) }, result.spanStyles)
    }

    @Test
    fun `insert before a style moves it`() {
        assertSpans("xxabc", highlighted("abc", 0, 3), 2 to 5)
    }

    @Test
    fun `insert after a style keeps it`() {
        assertSpans("abc dx", highlighted("abc d", 0, 3), 0 to 3)
    }

    @Test
    fun `insert inside a style grows it`() {
        assertSpans("abxc", highlighted("abc", 0, 3), 0 to 4)
    }

    @Test
    fun `insert at the end of a style grows it`() {
        assertSpans("abcdef", highlighted("abc", 0, 3), 0 to 6)
    }

    @Test
    fun `delete before a style moves it`() {
        assertSpans("cde", highlighted("abcde", 2, 5), 0 to 3)
    }

    @Test
    fun `delete inside a style shrinks it`() {
        assertSpans("ae", highlighted("abcde", 0, 5), 0 to 2)
    }

    @Test
    fun `delete across the end of a style cuts it`() {
        assertSpans("ab", highlighted("abcdef", 1, 5), 1 to 2)
    }

    @Test
    fun `delete of a whole style drops it`() {
        assertSpans("ab", highlighted("abcdef", 3, 5))
    }

    @Test
    fun `replacing a whole style keeps it on the new text`() {
        assertSpans("a xyz b", highlighted("a if b", 2, 4), 2 to 5)
    }

    @Test
    fun `repeated characters do not overlap prefix and suffix`() {
        assertSpans("aaa", highlighted("aa", 1, 2), 1 to 3)
    }

    @Test
    fun `clearing the text drops all styles`() {
        assertSpans("", highlighted("abc", 0, 3))
    }
}
