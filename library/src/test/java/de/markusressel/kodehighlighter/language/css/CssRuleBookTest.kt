package de.markusressel.kodehighlighter.language.css

import de.markusressel.kodehighlighter.language.css.rule.ColorRule
import de.markusressel.kodehighlighter.language.css.rule.CommentRule
import de.markusressel.kodehighlighter.language.css.rule.FunctionRule
import de.markusressel.kodehighlighter.language.css.rule.KeywordRule
import de.markusressel.kodehighlighter.language.css.rule.NumberRule
import de.markusressel.kodehighlighter.language.css.rule.PropertyRule
import de.markusressel.kodehighlighter.language.css.rule.SelectorRule
import de.markusressel.kodehighlighter.language.css.rule.StringRule
import de.markusressel.kodehighlighter.language.css.rule.TagSelectorRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class CssRuleBookTest {

    private val ruleBook = CssRuleBook()

    @Test
    fun `selectors and declarations`() {
        assertHighlighting(
            ruleBook,
            "a:hover, .b > #c::after,\ninput[type=x] { color: #fff; margin: -2px 1.5em }",
            TagSelectorRule to listOf("a", "input"),
            SelectorRule to listOf(":hover", ".b", "#c", "::after", "[type=x]"),
            PropertyRule to listOf("color", "margin"),
            ColorRule to listOf("#fff"),
            NumberRule to listOf("-2px", "1.5em"),
        )
    }

    @Test
    fun `at rules`() {
        assertHighlighting(
            ruleBook,
            "@media screen and (max-width: 600px) { p { width: 100%!important; } }",
            KeywordRule to listOf("@media", "!important"),
            TagSelectorRule to listOf("p"),
            PropertyRule to listOf("max-width", "width"),
            NumberRule to listOf("600px", "100%"),
        )
    }

    @Test
    fun `strings urls functions and comments`() {
        assertHighlighting(
            ruleBook,
            "a { background: url(//x/a.png), \"b/*c*/\"; --d: calc(1px * 2) } /* e { } */",
            TagSelectorRule to listOf("a"),
            PropertyRule to listOf("background", "--d"),
            FunctionRule to listOf("url", "calc"),
            NumberRule to listOf("1px", "2"),
            StringRule to listOf("(//x/a.png)", "\"b/*c*/\""),
            CommentRule to listOf("/* e { } */"),
        )
    }
}
