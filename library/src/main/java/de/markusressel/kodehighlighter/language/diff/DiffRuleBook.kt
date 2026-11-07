package de.markusressel.kodehighlighter.language.diff

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.diff.rule.AdditionRule
import de.markusressel.kodehighlighter.language.diff.rule.DeletionRule
import de.markusressel.kodehighlighter.language.diff.rule.FileRule
import de.markusressel.kodehighlighter.language.diff.rule.HunkRule

/**
 * Unified diff rule book
 */
open class DiffRuleBook : LanguageRuleBook {

    override val language: Language get() = DiffLanguage

    private val rules: List<LanguageRule> = listOf(
        FileRule,
        HunkRule,
        AdditionRule,
        DeletionRule,
    )

    override fun getRules() = rules

}
