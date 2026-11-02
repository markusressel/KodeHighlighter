package de.markusressel.kodehighlighter.demo

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Guards against patterns with catastrophic backtracking, quadratic scanning or stack overflows.
 * Runs on both the JVM (unit test) and ICU (instrumented test), since their regex engines differ.
 */
class RuleWorstCaseTest {

    private val unterminatedEscapes = "\"" + "\\a".repeat(50_000)
    private val longString = "\"" + "a".repeat(50_000) + "\""

    private val cases: List<Triple<String, LanguageRule, String>> = listOf(
        Triple("java comment, unterminated", de.markusressel.kodehighlighter.language.java.rule.CommentRule, unterminatedEscapes),
        Triple("java comment, long string", de.markusressel.kodehighlighter.language.java.rule.CommentRule, longString),
        Triple("kotlin comment, unterminated", de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule, unterminatedEscapes),
        Triple("kotlin comment, long string", de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule, longString),
        Triple("json string, unterminated", de.markusressel.kodehighlighter.language.json.rule.StringRule, unterminatedEscapes),
        Triple("json string, long string", de.markusressel.kodehighlighter.language.json.rule.StringRule, longString),
        Triple("ocaml string, unterminated", de.markusressel.kodehighlighter.language.ocaml.rule.StringRule, unterminatedEscapes),
        Triple("ocaml string, long string", de.markusressel.kodehighlighter.language.ocaml.rule.StringRule, longString),
        Triple("python comment, spaces", de.markusressel.kodehighlighter.language.python.rule.CommentRule, " ".repeat(50_000)),
        Triple("markdown list, newlines", de.markusressel.kodehighlighter.language.markdown.rule.ListRule, "\n".repeat(50_000)),
        Triple("markdown heading, newlines", de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule, "\n".repeat(50_000)),
    )

    @Test
    fun worstCaseInputsStayWithinBudget() {
        // regex matching can not be interrupted, so a stuck match must not block test shutdown
        val executor = Executors.newCachedThreadPool { task ->
            Thread(task).apply { isDaemon = true }
        }
        cases.forEach { (name, rule, input) ->
            val result = executor.submit(Callable { rule.findMatches(input) })
            try {
                result.get(BUDGET_MS, TimeUnit.MILLISECONDS)
            } catch (e: TimeoutException) {
                fail("$name took longer than $BUDGET_MS ms")
            }
        }
    }

    private companion object {
        const val BUDGET_MS = 2_000L
    }
}
