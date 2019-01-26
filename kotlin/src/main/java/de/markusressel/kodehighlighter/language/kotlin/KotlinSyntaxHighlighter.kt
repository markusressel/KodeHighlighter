package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.StatefulSyntaxHighlighter
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.kotlin.rule.*

class KotlinSyntaxHighlighter : StatefulSyntaxHighlighter() {

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(AnnotationRule(), ClassKeywordRule(), CommentRule(), ImportKeywordRule(), PackageKeywordRule(), ReturnKeywordRule(), FunctionKeywordRule(), VarKeywordRule(), NumberRule())
    }

    override fun getDefaultColorScheme(): SyntaxColorScheme {
        return DarkBackgroundColorScheme()
    }

}