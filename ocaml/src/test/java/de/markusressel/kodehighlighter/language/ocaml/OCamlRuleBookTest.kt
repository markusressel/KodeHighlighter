package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class OCamlRuleBookTest {

    private val ruleBook = OCamlRuleBook()

    @Test
    fun `nested comments are one comment`() {
        assertHighlighting(
            ruleBook,
            "(* a (* b *) let *) let x",
            CommentRule to listOf("(* a (* b *) let *)"),
            KeywordRule to listOf("let"),
        )
    }

    @Test
    fun `escaped backslash ends a string`() {
        assertHighlighting(ruleBook, "\"a\\\\\" ^ \"b\"", StringRule to listOf("\"a\\\\\"", "\"b\""))
    }

    @Test
    fun `quoted strings`() {
        assertHighlighting(ruleBook, "{|x\"y|} {id|z|id}", StringRule to listOf("{|x\"y|}", "{id|z|id}"))
    }

    @Test
    fun `char literals are not type variables`() {
        assertHighlighting(ruleBook, "'x' '\\n' 'a list", StringRule to listOf("'x'", "'\\n'"), )
    }

    @Test
    fun `bang keywords and nonrec`() {
        assertHighlighting(
            ruleBook,
            "open! inherit! let rec nonrec letter",
            KeywordRule to listOf("open!", "inherit!", "let", "rec", "nonrec"),
        )
    }

    @Test
    fun `modules numbers and booleans`() {
        assertHighlighting(
            ruleBook,
            "Some \$foo 0xFF true",
            ModuleRule to listOf("Some"),
            NumberRule to listOf("0xFF"),
            BooleanRule to listOf("true"),
        )
    }
}
