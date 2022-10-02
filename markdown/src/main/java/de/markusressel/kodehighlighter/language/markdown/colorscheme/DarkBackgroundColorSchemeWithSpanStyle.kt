package de.markusressel.kodehighlighter.language.markdown.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeLineRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.StrikeRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule

/**
 * A dark color scheme for markdown to be used for compose
 */
class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is BoldRule -> {
                setOf { SpanStyle(Color(0xFF0091EA), fontWeight = FontWeight.Bold) }
            }
            is ItalicRule -> {
                setOf { SpanStyle(Color(0xFF0091EA), fontStyle = FontStyle.Italic) }
            }
            is CodeInlineRule, is CodeLineRule -> {
                setOf { SpanStyle(Color(0xFF00C853)) }
            }
            is HeadingRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            is ImageLinkRule, is TextLinkRule -> {
                setOf { SpanStyle(Color(0xFF7C4DFF)) }
            }
            is StrikeRule -> {
                setOf { SpanStyle(Color(0xFF5D4037)) }
            }
            is ListRule -> {
                setOf { SpanStyle(Color(0xFF5f84d4)) }
            }
            else -> emptySet()
        }
    }

}