package de.markusressel.kodehighlighter.language.kotlin.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class VarKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): Sequence<MatchResult> {
        return PATTERN.findAll(spannable)
    }

    companion object {
        val PATTERN = "(lateinit\\s+)?var(?=\\s)|(const\\s+)?val(?=\\s)".toRegex()
    }

}