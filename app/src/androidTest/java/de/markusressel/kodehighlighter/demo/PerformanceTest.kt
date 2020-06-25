package de.markusressel.kodehighlighter.demo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.text.SpannableStringBuilder
import de.markusressel.kodehighlighter.core.RuleMatches
import de.markusressel.kodehighlighter.core.util.SpannableHighlighter
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
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
                "Java" to JavaRuleBook(),
                "JSON" to JsonRuleBook(),
                "Kotlin" to KotlinRuleBook(),
                "Markdown" to MarkdownRuleBook(),
                "Python" to PythonRuleBook()
        )

        val text = InstrumentationRegistry.getTargetContext().resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)

        runBlocking(Dispatchers.IO) {
            highlightersMap.map {
                val highlighter = SpannableHighlighter(it.value)
                val spannable = SpannableStringBuilder.valueOf(longText)

                var createAvg = 0L
                var applyAvg = 0L
                var totalAvg = 0L

                val runs = 10
                for (i in 1..runs) {
                    var stuff: List<RuleMatches> = emptyList()
                    val time1 = measureTimeMillis {
                        stuff = it.value.createHighlighting(spannable)
                    }
                    val time2 = measureTimeMillis {
                        highlighter.highlight(spannable, stuff)
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
        val markdownRuleBook = MarkdownRuleBook()
        val text = InstrumentationRegistry.getTargetContext().resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)
        val highlighter = SpannableHighlighter(markdownRuleBook)

        runBlocking(Dispatchers.IO) {
//            delay(15000)

            var createAvg = 0L
            var applyAvg = 0L
            var totalAvg = 0L

            val runs = 10
            for (i in 1..runs) {
                val spannable = SpannableStringBuilder.valueOf(longText)

                var stuff: List<RuleMatches> = emptyList()
                val time1 = measureTimeMillis {
                    stuff = markdownRuleBook.createHighlighting(spannable)
                }
                val time2 = measureTimeMillis {
                    highlighter.highlight(spannable, stuff)
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
