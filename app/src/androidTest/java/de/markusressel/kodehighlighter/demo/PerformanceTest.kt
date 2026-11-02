package de.markusressel.kodehighlighter.demo

import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.RuleMatches
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
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
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        assertEquals("de.markusressel.kodehighlighter.demo", appContext.packageName)
    }

    @Test
    fun performance_comparison() {
        val highlightersMap: Map<String, Pair<LanguageRuleBook, ColorScheme<CharacterStyle>>> = mapOf(
                "Java" to (JavaRuleBook() to de.markusressel.kodehighlighter.language.java.colorscheme.DarkBackgroundColorScheme()),
                "JSON" to (JsonRuleBook() to de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme()),
                "Kotlin" to (KotlinRuleBook() to de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorScheme()),
                "Markdown" to (MarkdownRuleBook() to de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme()),
                "Python" to (PythonRuleBook() to de.markusressel.kodehighlighter.language.python.colorscheme.DarkBackgroundColorScheme())
        )

        val text = InstrumentationRegistry.getInstrumentation().targetContext.resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)

        runBlocking(Dispatchers.IO) {
            highlightersMap.map {
                val (ruleBook, colorScheme) = it.value
                val highlighter = SpannableHighlighter(ruleBook, colorScheme)
                val spannable = SpannableStringBuilder.valueOf(longText)

                var createAvg = 0L
                var applyAvg = 0L
                var totalAvg = 0L

                val runs = 10
                for (i in 1..runs) {
                    var stuff: List<RuleMatches> = emptyList()
                    val time1 = measureTimeMillis {
                        stuff = ruleBook.createHighlighting(spannable)
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
        val text = InstrumentationRegistry.getInstrumentation().targetContext.resources.openRawResource(R.raw.markdown_sample).bufferedReader().readText()
        val longText = text.repeat(10)
        val highlighter = SpannableHighlighter(
            markdownRuleBook,
            de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorScheme()
        )

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
