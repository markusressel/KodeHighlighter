package de.markusressel.kodehighlighter.demo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.text.SpannableStringBuilder
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class PerformanceTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("de.markusressel.kodehighlighter.demo", appContext.packageName)
    }

    @Test
    fun performance() {
        val markdownSyntaxHighlighter = MarkdownSyntaxHighlighter()
        val text = InstrumentationRegistry.getTargetContext().resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)
        val spannable = SpannableStringBuilder.valueOf(longText)

        val time = measureTimeMillis {
            runBlocking {
                val stuff = markdownSyntaxHighlighter.createHighlighting(longText)
                markdownSyntaxHighlighter.highlight(spannable, stuff)
            }
        }

        print("highlighting took: $time ms")
        Assert.assertTrue(time < 100L)
    }
}
