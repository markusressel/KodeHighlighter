package de.markusressel.kodehighlighter.language.markdown.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.markdown.rule.AutolinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.BlockquoteRule
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeLineRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.StrikeRule
import de.markusressel.kodehighlighter.language.markdown.rule.TaskRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule
import de.markusressel.kodehighlighter.core.colorscheme.GitHubDarkTheme
import de.markusressel.kodehighlighter.language.markdown.rule.FrontMatterRule

/**
 * A dark color scheme for markdown to be used for compose. Rules of other languages, like in the front matter or in
 * code blocks, get the colors of [GitHubDarkTheme]
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
                setOf { SpanStyle(Color(0xFFB388FF)) }
            }
            is StrikeRule -> {
                setOf { SpanStyle(Color(0xFFA1887F)) }
            }
            is ListRule -> {
                setOf { SpanStyle(Color(0xFF5f84d4)) }
            }
            is CodeBlockRule -> {
                setOf { SpanStyle(Color(0xFF00C853)) }
            }
            is BlockquoteRule -> {
                setOf { SpanStyle(Color(0xFF86858B)) }
            }
            is HorizontalRuleRule -> {
                setOf { SpanStyle(Color(0xFF86858B)) }
            }
            is TaskRule -> {
                setOf { SpanStyle(Color(0xFF5f84d4)) }
            }
            is AutolinkRule -> {
                setOf { SpanStyle(Color(0xFFB388FF)) }
            }
            is FrontMatterRule -> {
                setOf { SpanStyle(Color(0xFF86858B)) }
            }
            // like the YAML of the front matter and languages in code blocks
            else -> GitHubDarkTheme.spanStyleColorScheme.getStyles(type)
        }
    }

}