package de.markusressel.kodehighlighter.language.json.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class NumericRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "\\b(-?(0x)?\\d*\\.?[\\da-f]+|NaN|-?Infinity)\\b".toRegex()
    }

}