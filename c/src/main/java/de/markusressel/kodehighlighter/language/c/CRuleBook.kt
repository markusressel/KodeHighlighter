package de.markusressel.kodehighlighter.language.c

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.c.rule.CommentRule
import de.markusressel.kodehighlighter.language.c.rule.KeywordRule
import de.markusressel.kodehighlighter.language.c.rule.LiteralRule
import de.markusressel.kodehighlighter.language.c.rule.NumberRule
import de.markusressel.kodehighlighter.language.c.rule.PreprocessorRule
import de.markusressel.kodehighlighter.language.c.rule.StringRule
import de.markusressel.kodehighlighter.language.c.rule.TypeRule

/**
 * C language rule book
 */
open class CRuleBook : RegionRuleBook() {

    override val language: Language get() = CLanguage

    override val scanner = CRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        PreprocessorRule,
        KeywordRule,
        TypeRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
