package de.markusressel.kodehighlighter.language.cpp

import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.cpp.rule.CommentRule
import de.markusressel.kodehighlighter.language.cpp.rule.KeywordRule
import de.markusressel.kodehighlighter.language.cpp.rule.LiteralRule
import de.markusressel.kodehighlighter.language.cpp.rule.NumberRule
import de.markusressel.kodehighlighter.language.cpp.rule.PreprocessorRule
import de.markusressel.kodehighlighter.language.cpp.rule.StringRule
import de.markusressel.kodehighlighter.language.cpp.rule.TypeRule

/**
 * C++ language rule book
 */
open class CppRuleBook : RegionRuleBook() {

    override val scanner = CppRegions.scanner

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
