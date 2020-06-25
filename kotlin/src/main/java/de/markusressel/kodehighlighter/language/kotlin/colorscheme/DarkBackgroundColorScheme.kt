package de.markusressel.kodehighlighter.language.kotlin.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.kotlin.rule.*

/**
 * A dark color scheme for kotlin
 */
class DarkBackgroundColorScheme : ColorScheme {

    override fun getStyles(type: LanguageRule): Set<StyleFactory> {
        return when (type) {
            is ImportKeywordRule,
            is PackageKeywordRule,
            is ClassKeywordRule,
            is OpenKeywordRule,
            is ReturnKeywordRule,
            is FunctionKeywordRule,
            is VisibilityKeywordRule,
            is VarKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is AnnotationRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#33691E")) }
            }
            is NumberRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#01579B")) }
            }
            else -> emptySet()
        }
    }

}