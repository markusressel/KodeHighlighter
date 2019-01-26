package de.markusressel.kodehighlighter.language.markdown.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class StrikeRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(~{2})([^~]+?)\\1".toRegex()
    }

}