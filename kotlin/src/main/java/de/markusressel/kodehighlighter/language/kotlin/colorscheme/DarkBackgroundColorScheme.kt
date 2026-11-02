package de.markusressel.kodehighlighter.language.kotlin.colorscheme

import android.graphics.Color
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
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
 * A dark color scheme for kotlin
 */
class DarkBackgroundColorScheme : ColorScheme<CharacterStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<CharacterStyle>> {
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
                setOf { ForegroundColorSpan(Color.parseColor("#6A9955")) }
            }
            is NumberRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#0091EA")) }
            }
            is ControlFlowKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is DeclarationKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is ModifierKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is LiteralRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is StringRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#6A8759")) }
            }
            else -> emptySet()
        }
    }

}