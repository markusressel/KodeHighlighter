package de.markusressel.kodehighlighter.language.rust

import de.markusressel.kodehighlighter.language.rust.rule.AttributeRule
import de.markusressel.kodehighlighter.language.rust.rule.CommentRule
import de.markusressel.kodehighlighter.language.rust.rule.KeywordRule
import de.markusressel.kodehighlighter.language.rust.rule.LifetimeRule
import de.markusressel.kodehighlighter.language.rust.rule.MacroRule
import de.markusressel.kodehighlighter.language.rust.rule.NumberRule
import de.markusressel.kodehighlighter.language.rust.rule.StringRule
import de.markusressel.kodehighlighter.language.rust.rule.TypeRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class RustRuleBookTest {

    private val ruleBook = RustRuleBook()

    @Test
    fun `raw strings`() {
        assertHighlighting(
            ruleBook,
            "r#\"a\"b\"# br\"c\" bar\"d\"",
            StringRule to listOf("r#\"a\"b\"#", "br\"c\"", "\"d\""),
        )
    }

    @Test
    fun `nested comments`() {
        assertHighlighting(
            ruleBook,
            "/* a /* b */ fn */ fn",
            CommentRule to listOf("/* a /* b */ fn */"),
            KeywordRule to listOf("fn"),
        )
    }

    @Test
    fun `lifetimes are not chars`() {
        assertHighlighting(
            ruleBook,
            "&'a str, 'b' '\\n' b'c' '\\u{1F600}'",
            LifetimeRule to listOf("'a"),
            TypeRule to listOf("str"),
            StringRule to listOf("'b'", "'\\n'", "b'c'", "'\\u{1F600}'"),
        )
    }

    @Test
    fun `two lifetimes`() {
        assertHighlighting(
            ruleBook,
            "<'a, 'b>",
            LifetimeRule to listOf("'a", "'b"),
        )
    }

    @Test
    fun `macros and attributes`() {
        assertHighlighting(
            ruleBook,
            "#[derive(Debug)] println!(x) a != b",
            AttributeRule to listOf("#[derive(Debug)]"),
            MacroRule to listOf("println!"),
        )
    }

    @Test
    fun `numbers and ranges`() {
        assertHighlighting(
            ruleBook,
            "a1 0xFF 1_000u32 1.5f64 0..10",
            NumberRule to listOf("0xFF", "1_000u32", "1.5f64", "0"),
        )
    }
}
