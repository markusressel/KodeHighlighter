package de.markusressel.kodehighlighter.language.markdown.colorscheme

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.markdown.rule.*
import de.markusressel.kodehighlighter.language.markdown.rule.AutolinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.BlockquoteRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.TaskRule
import de.markusressel.kodehighlighter.core.colorscheme.GitHubDarkTheme
import de.markusressel.kodehighlighter.language.markdown.rule.FrontMatterRule

/**
 * A dark color scheme for markdown. Rules of other languages, like in the front matter or in code blocks, get the
 * colors of [GitHubDarkTheme]
 */
class DarkBackgroundColorScheme : ColorScheme<CharacterStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<CharacterStyle>> {
        return when (type) {
            is BoldRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#0091EA")) },
                        { StyleSpan(Typeface.BOLD) })
            }
            is ItalicRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#0091EA")) }, { StyleSpan(Typeface.ITALIC) })
            }
            is CodeInlineRule, is CodeLineRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#00C853")) }
            }
            is HeadingRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is ImageLinkRule, is TextLinkRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#B388FF")) }
            }
            is StrikeRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#A1887F")) }
            }
            is ListRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#5f84d4")) }
            }
            is CodeBlockRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#00C853")) }
            }
            is BlockquoteRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#86858B")) }
            }
            is HorizontalRuleRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#86858B")) }
            }
            is TaskRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#5f84d4")) }
            }
            is AutolinkRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#B388FF")) }
            }
            is FrontMatterRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#86858B")) }
            }
            // like the YAML of the front matter and languages in code blocks
            else -> GitHubDarkTheme.characterStyleColorScheme.getStyles(type)
        }
    }

}