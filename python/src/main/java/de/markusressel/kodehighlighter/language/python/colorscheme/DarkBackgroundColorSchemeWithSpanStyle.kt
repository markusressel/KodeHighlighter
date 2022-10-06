package de.markusressel.kodehighlighter.language.python.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.python.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.CommentRule
import de.markusressel.kodehighlighter.language.python.rule.DecoratorRule
import de.markusressel.kodehighlighter.language.python.rule.DefKeyword
import de.markusressel.kodehighlighter.language.python.rule.ExceptRule
import de.markusressel.kodehighlighter.language.python.rule.InternalFunctionsRule
import de.markusressel.kodehighlighter.language.python.rule.MagicRule
import de.markusressel.kodehighlighter.language.python.rule.OtherKeywordsRule
import de.markusressel.kodehighlighter.language.python.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.SelfKeywordRule
import de.markusressel.kodehighlighter.language.python.rule.TypeKeywordRule
import java.util.Collections.emptySet

/**
 * A dark color scheme for python to be used for compose
 */
class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is ClassKeywordRule,
            is DefKeyword,
            is InternalFunctionsRule,
            is OtherKeywordsRule,
            is ReturnKeywordRule,
            is SelfKeywordRule,
            is TypeKeywordRule,
            is ExceptRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is DecoratorRule -> {
                setOf { SpanStyle(Color(0xFFFBC02D)) }
            }
            is CommentRule -> {
                setOf { SpanStyle(Color(0xFF33691E)) }
            }
            is MagicRule -> {
                setOf { SpanStyle(Color(0xFF7C4DFF)) }
            }
            else -> emptySet()
        }
    }

}