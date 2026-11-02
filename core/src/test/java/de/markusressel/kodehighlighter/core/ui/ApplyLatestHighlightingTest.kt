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

    @Test
    fun `grown text keeps styles`() {
        val result = applyLatestHighlighting("abcdef", highlighted("abc", 0, 3))
        assertEquals("abcdef", result.text)
        assertEquals(listOf(AnnotatedString.Range(style, 0, 3)), result.spanStyles)
    }

    @Test
    fun `shrunk text cuts styles to the new length`() {
        val result = applyLatestHighlighting("ab", highlighted("abcdef", 1, 5))
        assertEquals(listOf(AnnotatedString.Range(style, 1, 2)), result.spanStyles)
    }

    @Test
    fun `styles beyond the new length are dropped`() {
        val result = applyLatestHighlighting("ab", highlighted("abcdef", 3, 5))
        assertEquals(emptyList<AnnotatedString.Range<SpanStyle>>(), result.spanStyles)
    }
}
