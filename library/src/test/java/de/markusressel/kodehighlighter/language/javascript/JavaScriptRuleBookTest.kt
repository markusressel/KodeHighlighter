package de.markusressel.kodehighlighter.language.javascript

import de.markusressel.kodehighlighter.language.javascript.rule.CommentRule
import de.markusressel.kodehighlighter.language.javascript.rule.FunctionRule
import de.markusressel.kodehighlighter.language.javascript.rule.KeywordRule
import de.markusressel.kodehighlighter.language.javascript.rule.LiteralRule
import de.markusressel.kodehighlighter.language.javascript.rule.NumberRule
import de.markusressel.kodehighlighter.language.javascript.rule.RegexRule
import de.markusressel.kodehighlighter.language.javascript.rule.StringRule
import de.markusressel.kodehighlighter.language.javascript.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class JavaScriptRuleBookTest {

    private val ruleBook = JavaScriptRuleBook()

    @Test
    fun `keywords literals and numbers`() {
        assertHighlighting(
            ruleBook,
            "const a = 0x1F + 1_000n + .5e3 + 2; let b = null ?? undefined, c = true",
            KeywordRule to listOf("const", "let"),
            LiteralRule to listOf("null", "undefined", "true"),
            NumberRule to listOf("0x1F", "1_000n", ".5e3", "2"),
        )
    }

    @Test
    fun `strings templates and comments`() {
        assertHighlighting(
            ruleBook,
            "s = 'a' + \"b\\\"c\" + `x\${y}\n// z` // c\n/* d */",
            StringRule to listOf("'a'", "\"b\\\"c\"", "`x\${y}\n// z`"),
            CommentRule to listOf("// c", "/* d */"),
        )
    }

    @Test
    fun `regex literals and division`() {
        assertHighlighting(
            ruleBook,
            "x = a / b / 2;\ny = /a[/]b+\\/c/gi.test(s); return /x/",
            KeywordRule to listOf("return"),
            NumberRule to listOf("2"),
            RegexRule to listOf("/a[/]b+\\/c/gi", "/x/"),
        )
    }

    @Test
    fun `functions classes and methods`() {
        assertHighlighting(
            ruleBook,
            "function foo() {}\nconst bar = (a) => a;\nclass A extends B {\n    get size() { return new Map(); }\n    if (x) {}\n}",
            KeywordRule to listOf("function", "const", "class", "extends", "get", "return", "new", "if"),
            FunctionRule to listOf("foo", "bar", "size"),
            TypeRule to listOf("A", "B", "Map"),
        )
    }

    @Test
    fun `members are not keywords`() {
        assertHighlighting(
            ruleBook,
            "map.delete(x); obj.class = this.default; ...this",
            KeywordRule to listOf("this", "this"),
        )
    }

    @Test
    fun `hashbang`() {
        assertHighlighting(
            ruleBook,
            "#!/usr/bin/env node\nlet a",
            CommentRule to listOf("#!/usr/bin/env node"),
            KeywordRule to listOf("let"),
        )
    }
}
