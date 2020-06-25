package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.java.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.java.rule.*

/**
 * Java syntax highlighter
 */
class JavaSyntaxHighlighter : SyntaxHighlighter {

    override val defaultColorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
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