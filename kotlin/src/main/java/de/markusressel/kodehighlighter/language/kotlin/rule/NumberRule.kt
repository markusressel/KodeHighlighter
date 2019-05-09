package de.markusressel.kodehighlighter.language.kotlin.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class NumberRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(-)?\\d+(\\.\\d+)?(L)?".toRegex()
    }

}