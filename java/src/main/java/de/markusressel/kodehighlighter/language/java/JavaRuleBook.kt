package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.FinalKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.StaticKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.language.java.colorscheme.rule.VisibilityKeywordRule

/**
 * Java language rule book
 */
class JavaRuleBook : LanguageRuleBook {

    override fun getRules(): List<LanguageRule> {
        return listOf(
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