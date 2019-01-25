package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.kotlin.rule.*
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterBase
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class KotlinSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(AnnotationRule(), ClassKeywordRule(), CommentRule(), ImportKeywordRule(), PackageKeywordRule(), ReturnKeywordRule(), FunctionKeywordRule(), VarKeywordRule(), NumberRule())
    }

    override fun getDefaultColorScheme(): SyntaxColorScheme {
        return DarkBackgroundColorScheme()
    }

}