package de.markusressel.kodehighlighter.language.python.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.python.rule.*
import java.util.Collections.emptySet

/**
 * A dark color scheme for java
 */
class DarkBackgroundColorScheme : SyntaxColorScheme {

    override fun getStyles(type: SyntaxHighlighterRule): Set<StyleFactory> {
        return when (type) {
            is ClassKeywordRule,
            is DefKeyword,
            is InternalFunctionsRule,
            is OtherKeywordsRule,
            is ReturnKeywordRule,
            is SelfKeywordRule,
            is TypeKeywordRule,
            is ExceptRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is DecoratorRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FBC02D")) }
            }
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#33691E")) }
            }
            is MagicRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#7C4DFF")) }
            }
            else -> emptySet()
        }
    }

}