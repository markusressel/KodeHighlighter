package de.markusressel.kodehighlighter.language.python.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class OtherKeywordsRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "\\b(pass|lambda|with|is|not|in|from|elif|raise|del)(?=\\b)".toRegex()
    }

}