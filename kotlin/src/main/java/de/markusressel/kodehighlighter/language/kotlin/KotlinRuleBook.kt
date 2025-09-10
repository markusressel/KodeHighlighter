package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.language.kotlin.rule.*

/**
 * Kotlin language rule book
 */
class KotlinRuleBook : LanguageRuleBook {

    private val rules = listOf(
        AnnotationRule,
        ClassKeywordRule,
        ImportKeywordRule,
        PackageKeywordRule,
        ReturnKeywordRule,
        FunctionKeywordRule,
        VarKeywordRule,
        NumberRule,
        CommentRule,
        VisibilityKeywordRule
    )

    override fun getRules() = rules

}