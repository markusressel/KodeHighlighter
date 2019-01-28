package de.markusressel.kodehighlighter.language.python.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class ExceptRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(except) (\\w+):".toRegex()
    }

}