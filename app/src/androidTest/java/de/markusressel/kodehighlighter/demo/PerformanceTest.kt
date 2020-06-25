package de.markusressel.kodehighlighter.demo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.text.SpannableStringBuilder
import de.markusressel.kodehighlighter.core.HighlightEntity
import de.markusressel.kodehighlighter.language.java.JavaSyntaxHighlighter
import de.markusressel.kodehighlighter.language.json.JsonSyntaxHighlighter
import de.markusressel.kodehighlighter.language.kotlin.KotlinSyntaxHighlighter
import de.markusressel.kodehighlighter.language.markdown.MarkdownSyntaxHighlighter
import de.markusressel.kodehighlighter.language.python.PythonSyntaxHighlighter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
    fun performance_comparison() {
        val highlightersMap = mapOf(
                "Java" to JavaSyntaxHighlighter(),
                "JSON" to JsonSyntaxHighlighter(),
                "Kotlin" to KotlinSyntaxHighlighter(),
                "Markdown" to MarkdownSyntaxHighlighter(),
                "Python" to PythonSyntaxHighlighter()
        )

        val text = InstrumentationRegistry.getTargetContext().resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)

        runBlocking(Dispatchers.IO) {
            highlightersMap.map {
                val spannable = SpannableStringBuilder.valueOf(longText)

                var createAvg = 0L
                var applyAvg = 0L
                var totalAvg = 0L

                val runs = 10
                for (i in 1..runs) {
                    var stuff: List<HighlightEntity> = emptyList()
                    val time1 = measureTimeMillis {
                        stuff = it.value.createHighlighting(spannable)
                    }
                    val time2 = measureTimeMillis {
                        it.value.highlight(spannable, stuff)
                    }

//                    println("${it.key} Create: $time1")
//                    println("${it.key} Apply: $time2")
//                    println("${it.key} Total: ${time1 + time2}")

                    createAvg += time1
                    applyAvg += time2
                    totalAvg += time1 + time2
                }

                println("${it.key} AVERAGES")
                println("${it.key} Create: ${createAvg / runs}")
                println("${it.key} Apply: ${applyAvg / runs}")
                println("${it.key} Total: ${totalAvg / runs}")

                it.key
                it.value
            }
        }

    }

    @Test
    fun performance() {
        val markdownSyntaxHighlighter = MarkdownSyntaxHighlighter()
        val text = InstrumentationRegistry.getTargetContext().resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)
        val spannable = SpannableStringBuilder.valueOf(longText)

        runBlocking(Dispatchers.IO) {
            var createAvg = 0L
            var applyAvg = 0L
            var totalAvg = 0L

            val runs = 10
            for (i in 1..runs) {
                var stuff: List<HighlightEntity> = emptyList()
                val time1 = measureTimeMillis {
                    stuff = markdownSyntaxHighlighter.createHighlighting(spannable)
                }
                val time2 = measureTimeMillis {
                    markdownSyntaxHighlighter.highlight(spannable, stuff)
                }

                println("Create: $time1")
                println("Apply: $time2")
                println("Total: ${time1 + time2}")

                createAvg += time1
                applyAvg += time2
                totalAvg += time1 + time2
            }

            println("AVERAGES")
            println("Create: ${createAvg / runs}")
            println("Apply: ${applyAvg / runs}")
            println("Total: ${totalAvg / runs}")
        }
    }
}
