package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.kotlin.rule.*

/**
 * Kotlin language rule book
 */
class KotlinRuleBook : LanguageRuleBook {

    override val targetLanguages = setOf(KotlinLanguage)

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<LanguageRule> {
        return setOf(
                AnnotationRule(),
                ClassKeywordRule(),
                CommentRule(),
                ImportKeywordRule(),
                PackageKeywordRule(),
                ReturnKeywordRule(),
                FunctionKeywordRule(),
                VarKeywordRule(),
                NumberRule())
    }

}