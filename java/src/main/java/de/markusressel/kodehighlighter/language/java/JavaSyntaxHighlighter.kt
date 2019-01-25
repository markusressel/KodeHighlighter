package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.language.java.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.java.rule.*
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterBase
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class JavaSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(PackageKeywordRule(), ImportKeywordRule(), ClassKeywordRule(), AnnotationRule(), TypeKeywordRule(), ReturnKeywordRule(), VisibilityKeywordRule(), CommentRule())
    }

    override fun getDefaultColorScheme(): SyntaxColorScheme {
        return DarkBackgroundColorScheme()
    }

}