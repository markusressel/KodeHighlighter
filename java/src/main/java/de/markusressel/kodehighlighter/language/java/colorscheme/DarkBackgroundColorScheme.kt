package de.markusressel.kodehighlighter.language.java.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.java.rule.*
import java.util.Collections.emptySet

/**
 * A dark color scheme for markdown text
 */
class DarkBackgroundColorScheme : SyntaxColorScheme {

    override fun getStyles(type: SyntaxHighlighterRule): Set<StyleFactory> {
        return when (type) {
            is ImportKeywordRule, is PackageKeywordRule, is ClassKeywordRule, is TypeKeywordRule, is VisibilityKeywordRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#FF6D00")) })
            }
            is AnnotationRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#FBC02D")) })
            }
            is CommentRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#33691E")) })
            }
            else -> emptySet()
        }
    }

}