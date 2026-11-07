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
import de.markusressel.kodehighlighter.language.yaml.rule.AnchorRule as YamlAnchorRule
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule as YamlCommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.DocumentRule as YamlDocumentRule
import de.markusressel.kodehighlighter.language.yaml.rule.KeyRule as YamlKeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.ListRule as YamlListRule
import de.markusressel.kodehighlighter.language.yaml.rule.LiteralRule as YamlLiteralRule
import de.markusressel.kodehighlighter.language.yaml.rule.NumberRule as YamlNumberRule
import de.markusressel.kodehighlighter.language.yaml.rule.QuotedKeyRule as YamlQuotedKeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.StringRule as YamlStringRule

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
            // YAML front matter
            is YamlKeyRule, is YamlQuotedKeyRule -> {
                setOf { SpanStyle(Color(0xFFFF9E80)) }
            }
            is YamlStringRule -> {
                setOf { SpanStyle(Color(0xFF00C853)) }
            }
            is YamlNumberRule, is YamlLiteralRule -> {
                setOf { SpanStyle(Color(0xFF40C4FF)) }
            }
            is YamlAnchorRule -> {
                setOf { SpanStyle(Color(0xFFB388FF)) }
            }
            is YamlCommentRule, is YamlDocumentRule, is YamlListRule -> {
                setOf { SpanStyle(Color(0xFF86858B)) }
            }
            else -> emptySet()
        }
    }

}