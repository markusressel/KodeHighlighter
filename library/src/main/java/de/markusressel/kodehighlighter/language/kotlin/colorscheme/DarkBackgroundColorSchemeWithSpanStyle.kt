package de.markusressel.kodehighlighter.language.kotlin.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.kotlin.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ControlFlowKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.DeclarationKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.FunctionKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.LiteralRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ModifierKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.NumberRule
import de.markusressel.kodehighlighter.language.kotlin.rule.OpenKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.StringRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VarKeywordRule
import de.markusressel.kodehighlighter.language.kotlin.rule.VisibilityKeywordRule

/**
 * A dark color scheme for kotlin to be used for compose
 */
class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is ImportKeywordRule,
            is PackageKeywordRule,
            is ClassKeywordRule,
            is OpenKeywordRule,
            is ReturnKeywordRule,
            is FunctionKeywordRule,
            is VisibilityKeywordRule,
            is VarKeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is AnnotationRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is CommentRule -> {
                setOf { SpanStyle(Color(0xFF6A9955)) }
            }
            is NumberRule -> {
                setOf { SpanStyle(Color(0xFF0091EA)) }
            }
            is ControlFlowKeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is DeclarationKeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is ModifierKeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is LiteralRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is StringRule -> {
                setOf { SpanStyle(Color(0xFF6A8759)) }
            }
            else -> emptySet()
        }
    }

}