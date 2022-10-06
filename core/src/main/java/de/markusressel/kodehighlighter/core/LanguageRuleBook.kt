package de.markusressel.kodehighlighter.core

import android.text.Spannable
import android.text.style.CharacterStyle
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * A function that creates a style like [CharacterStyle] or [SpanStyle] that can be applied to a [Spannable] or [CharSequence]
 */
typealias StyleFactory<Style> = () -> Style

/**
 * Interface for a language rule book, providing definitions of what to highlight
 */
interface LanguageRuleBook {

    /**
     * Get a set of rules for this highlighter
     */
    fun getRules(): List<LanguageRule>

    /**
     * Analyzes the given text and creates a list of styles that would need to be applied
     * to it.
     *
     * @param charSequence the text to analyze
     * @return list of highlighting entities
     */
    suspend fun createHighlighting(charSequence: CharSequence): List<RuleMatches> {
        return coroutineScope {
            getRules().map { rule ->
                async {
                    val matches = rule.findMatches(charSequence)
                    if (matches.isNotEmpty()) {
                        RuleMatches(rule, matches)
                    } else {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }

}