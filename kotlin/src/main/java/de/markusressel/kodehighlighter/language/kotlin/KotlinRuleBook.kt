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

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

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
                CommentRule())
    }

}