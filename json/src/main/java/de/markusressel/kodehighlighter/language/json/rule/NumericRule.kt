package de.markusressel.kodehighlighter.language.json.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class NumericRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "\\b(-?(0x)?\\d*\\.?[\\da-f]+|NaN|-?Infinity)\\b".toRegex()
    }

}