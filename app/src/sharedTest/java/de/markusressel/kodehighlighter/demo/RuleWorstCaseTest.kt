package de.markusressel.kodehighlighter.demo

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.diff.DiffRuleBook
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.properties.PropertiesRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import de.markusressel.kodehighlighter.language.dart.DartRuleBook
import de.markusressel.kodehighlighter.language.swift.SwiftRuleBook
import de.markusressel.kodehighlighter.language.rust.RustRuleBook
import de.markusressel.kodehighlighter.language.go.GoRuleBook
import de.markusressel.kodehighlighter.language.csharp.CSharpRuleBook
import de.markusressel.kodehighlighter.language.cpp.CppRuleBook
import de.markusressel.kodehighlighter.language.c.CRuleBook
import de.markusressel.kodehighlighter.language.toml.TomlRuleBook
import de.markusressel.kodehighlighter.language.xml.XmlRuleBook
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook
import de.markusressel.kodehighlighter.language.css.CssRuleBook
import de.markusressel.kodehighlighter.language.generic.GenericRuleBook
import de.markusressel.kodehighlighter.language.html.HtmlRuleBook
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRuleBook
import kotlinx.coroutines.runBlocking
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

    @Test
    fun ruleBooksStayWithinBudgetOnWorstCaseInputs() {
        val ruleBooks = listOf(
            JavaRuleBook(),
            JsonRuleBook(),
            KotlinRuleBook(),
            MarkdownRuleBook(),
            OCamlRuleBook(),
            XmlRuleBook(),
            YamlRuleBook(),
            TomlRuleBook(),
            PropertiesRuleBook(),
            DiffRuleBook(),
            CRuleBook(),
            CppRuleBook(),
            CSharpRuleBook(),
            GoRuleBook(),
            RustRuleBook(),
            SwiftRuleBook(),
            DartRuleBook(),
            PythonRuleBook(),
            JavaScriptRuleBook(),
            CssRuleBook(),
            HtmlRuleBook(),
            GenericRuleBook(),
        )
        val inputs = listOf(
            unterminatedEscapes,
            longString,
            " ".repeat(50_000),
            "\n".repeat(50_000),
            "r".repeat(50_000),
            "' ".repeat(25_000),
            "\" \n".repeat(20_000),
            "` `` ".repeat(10_000),
            "```\n".repeat(10_000),
            "(* ".repeat(20_000),
            "/* ".repeat(20_000),
            "**a ".repeat(15_000),
            "_a ".repeat(15_000),
            "a" + " ".repeat(50_000),
            "0x" + "1".repeat(50_000),
            "0b" + "1".repeat(50_000),
            "- ".repeat(25_000),
            "a: ".repeat(15_000),
            "<".repeat(50_000),
            "<!DOCTYPE ".repeat(5_000),
            "<a b='".repeat(8_000),
            "#".repeat(50_000),
            "#[".repeat(25_000),
            "![".repeat(25_000),
            "[a](b".repeat(10_000),
            "r##\"".repeat(12_000),
            "R\"x(".repeat(12_000),
            "= /".repeat(15_000),
            "/ ".repeat(25_000),
            "<script ".repeat(6_000),
            "<script>".repeat(6_000),
            "url(".repeat(12_000),
            "a,".repeat(25_000),
            "a:b ".repeat(12_000),
            "#fff ".repeat(10_000),
            "f(".repeat(25_000),
        )
        val executor = Executors.newCachedThreadPool { task ->
            Thread(task).apply { isDaemon = true }
        }
        ruleBooks.forEach { ruleBook ->
            inputs.forEach { input ->
                val result = executor.submit(Callable { runBlocking { ruleBook.createHighlighting(input) } })
                try {
                    result.get(BUDGET_MS, TimeUnit.MILLISECONDS)
                } catch (e: TimeoutException) {
                    fail("${ruleBook::class.simpleName} took longer than $BUDGET_MS ms on ${input.take(10)}…")
                }
            }
        }
    }

    private companion object {
        const val BUDGET_MS = 2_000L
    }
}
