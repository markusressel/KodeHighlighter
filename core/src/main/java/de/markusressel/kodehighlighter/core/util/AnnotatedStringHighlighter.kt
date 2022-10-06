package de.markusressel.kodehighlighter.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.RuleMatches
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme

/**
 * Manages the interaction between a [AnnotatedString] and a [LanguageRuleBook]
 */
open class AnnotatedStringHighlighter(
        private val languageRuleBook: LanguageRuleBook,
        private val colorScheme: ColorScheme<SpanStyle>
) : LanguageRuleBook by languageRuleBook {

    /**
     * Highlight the given text and returns [AnnotatedString]
     *
     * @param charSequence the [CharSequence] to apply highlighting to
     */
    open suspend fun highlight(charSequence: CharSequence): AnnotatedString {
        val highlightEntities = createHighlighting(charSequence)
        return highlight(charSequence, highlightEntities)
    }

    /**
     * Highlight the given text and returns
     *
     * @param charSequence the [CharSequence] to apply highlighting to
     * @param ruleMatches a list of [RuleMatches] objects that hold the styles to apply
     */
    open suspend fun highlight(charSequence: CharSequence, ruleMatches: List<RuleMatches>): AnnotatedString {
        return buildAnnotatedString {
            append(charSequence.toString())
            ruleMatches.forEach {
                val styleFactories = colorScheme.getStyles(it.rule)
                it.matches.forEach { match ->
                    styleFactories.forEach { styleFactory ->
                        addStyle(styleFactory(), match.startIndex, match.endIndex)
                    }
                }
            }
        }
    }
}