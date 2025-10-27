package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.java.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.FinalKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StaticKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.VisibilityKeywordRule

/**
 * Java language rule book
 */
open class JavaRuleBook : LanguageRuleBook {

    private val rules = listOf(
        PackageKeywordRule,
        ImportKeywordRule,
        ClassKeywordRule,
        AnnotationRule,
        TypeKeywordRule,
        FinalKeywordRule,
        StaticKeywordRule,
        ReturnKeywordRule,
        VisibilityKeywordRule,
        CommentRule
    )

    override fun getRules() = rules

}