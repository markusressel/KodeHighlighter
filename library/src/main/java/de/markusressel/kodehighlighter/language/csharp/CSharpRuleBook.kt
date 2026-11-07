package de.markusressel.kodehighlighter.language.csharp

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.csharp.rule.CommentRule
import de.markusressel.kodehighlighter.language.csharp.rule.KeywordRule
import de.markusressel.kodehighlighter.language.csharp.rule.LiteralRule
import de.markusressel.kodehighlighter.language.csharp.rule.NumberRule
import de.markusressel.kodehighlighter.language.csharp.rule.PreprocessorRule
import de.markusressel.kodehighlighter.language.csharp.rule.StringRule
import de.markusressel.kodehighlighter.language.csharp.rule.TypeRule

/**
 * C# language rule book
 */
open class CSharpRuleBook : RegionRuleBook() {

    override val language: Language get() = CSharpLanguage

    override val scanner = CSharpRegions.scanner

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
