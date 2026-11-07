package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.kotlin.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ControlFlowKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.DeclarationKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.FunctionKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.LiteralRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ModifierKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.NumberRule
import de.markusressel.kodehighlighter.language.kotlin.rule.OpenKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.StringRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VarKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VisibilityKeywordRule

/**
 * Kotlin language rule book
 */
open class KotlinRuleBook : RegionRuleBook() {

    override val language: Language get() = KotlinLanguage

    override val scanner = KotlinRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        AnnotationRule,
        ClassKeywordRule,
        ImportKeywordRule,
        PackageKeywordRule,
        ReturnKeywordRule,
        FunctionKeywordRule,
        VarKeywordRule,
        OpenKeywordRule,
        VisibilityKeywordRule,
        ControlFlowKeywordRule,
        DeclarationKeywordRule,
        ModifierKeywordRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule
    )

    override fun getRules() = rules

}
