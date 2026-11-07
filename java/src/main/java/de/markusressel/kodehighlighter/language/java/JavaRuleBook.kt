package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.java.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.ControlFlowKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.DeclarationKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.FinalKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.LiteralRule
import de.markusressel.kodehighlighter.language.java.rule.ModifierKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.NumberRule
import de.markusressel.kodehighlighter.language.java.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StaticKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StringRule
import de.markusressel.kodehighlighter.language.java.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.VisibilityKeywordRule

/**
 * Java language rule book
 */
open class JavaRuleBook : RegionRuleBook() {

    override val language: Language get() = JavaLanguage

    override val scanner = JavaRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        PackageKeywordRule,
        ImportKeywordRule,
        ClassKeywordRule,
        AnnotationRule,
        TypeKeywordRule,
        FinalKeywordRule,
        StaticKeywordRule,
        ReturnKeywordRule,
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
