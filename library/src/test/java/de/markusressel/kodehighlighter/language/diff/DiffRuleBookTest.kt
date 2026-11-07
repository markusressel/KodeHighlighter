package de.markusressel.kodehighlighter.language.diff

import de.markusressel.kodehighlighter.language.diff.rule.AdditionRule
import de.markusressel.kodehighlighter.language.diff.rule.DeletionRule
import de.markusressel.kodehighlighter.language.diff.rule.FileRule
import de.markusressel.kodehighlighter.language.diff.rule.HunkRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class DiffRuleBookTest {

    private val ruleBook = DiffRuleBook()

    @Test
    fun `all line types`() {
        assertHighlighting(
            ruleBook,
            "diff --git a b\n--- a\n+++ b\n@@ -1 +1 @@ c\n d\n-e\n+f\n",
            FileRule to listOf("diff --git a b", "--- a", "+++ b"),
            HunkRule to listOf("@@ -1 +1 @@ c"),
            DeletionRule to listOf("-e"),
            AdditionRule to listOf("+f"),
        )
    }

    @Test
    fun `markers only count at the start of a line`() {
        assertHighlighting(
            ruleBook,
            "a + b - c @@ d",

        )
    }

    @Test
    fun `changed lines that look like headers`() {
        assertHighlighting(
            ruleBook,
            "+++b\n---c",
            AdditionRule to listOf("+++b"),
            DeletionRule to listOf("---c"),
        )
    }
}
