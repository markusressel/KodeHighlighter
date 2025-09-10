package de.markusressel.kodehighlighter.language.java.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.java.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.FinalKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StaticKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.VisibilityKeywordRule
import java.util.Collections.emptySet

/**
 * A dark color scheme for java to be used for compose
 */
class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is ImportKeywordRule,
            is PackageKeywordRule,
            is ClassKeywordRule,
            is TypeKeywordRule,
            is StaticKeywordRule,
            is ReturnKeywordRule,
            is FinalKeywordRule,
            is VisibilityKeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is AnnotationRule -> {
                setOf { SpanStyle(Color(0xFFFBC02D)) }
            }
            is CommentRule -> {
                setOf { SpanStyle(Color(0xFF33691E)) }
            }
            else -> emptySet()
        }
    }

}