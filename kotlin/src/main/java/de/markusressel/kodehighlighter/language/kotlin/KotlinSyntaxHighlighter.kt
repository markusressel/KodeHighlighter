package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.kotlin.rule.*

class KotlinSyntaxHighlighter : SyntaxHighlighter {

    override var colorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
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