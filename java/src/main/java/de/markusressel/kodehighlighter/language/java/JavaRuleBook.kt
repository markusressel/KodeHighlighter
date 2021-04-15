package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.java.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.java.rule.*

/**
 * Java language rule book
 */
class JavaRuleBook : LanguageRuleBook {

    override val targetLanguages = setOf(JavaLanguage)

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<LanguageRule> {
        return setOf(
                PackageKeywordRule(),
                ImportKeywordRule(),
                ClassKeywordRule(),
                AnnotationRule(),
                TypeKeywordRule(),
                FinalKeywordRule(),
                StaticKeywordRule(),
                ReturnKeywordRule(),
                VisibilityKeywordRule(),
                CommentRule())
    }

}