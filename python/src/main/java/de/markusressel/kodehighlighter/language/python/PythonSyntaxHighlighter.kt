package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.python.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.python.rule.*

/**
 * Python syntax highlighter
 */
class PythonSyntaxHighlighter : SyntaxHighlighter {

    override var colorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(
                ClassKeywordRule(),
                CommentRule(),
                DecoratorRule(),
                DefKeyword(),
                ExceptRule(),
                InternalFunctionsRule(),
                MagicRule(),
                OtherKeywordsRule(),
                ReturnKeywordRule(),
                SelfKeywordRule(),
                TypeKeywordRule())
    }

}