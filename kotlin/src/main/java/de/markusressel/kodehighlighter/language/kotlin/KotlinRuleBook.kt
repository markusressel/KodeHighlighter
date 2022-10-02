package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.kotlin.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule
import de.markusressel.kodehighlighter.language.kotlin.rule.FunctionKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.NumberRule
import de.markusressel.kodehighlighter.language.kotlin.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VarKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VisibilityKeywordRule

/**
 * Kotlin language rule book
 */
class KotlinRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
                AnnotationRule(),
                ClassKeywordRule(),
                ImportKeywordRule(),
                PackageKeywordRule(),
                ReturnKeywordRule(),
                FunctionKeywordRule(),
                VarKeywordRule(),
                NumberRule(),
                CommentRule(),
                VisibilityKeywordRule()
        )
    }

}