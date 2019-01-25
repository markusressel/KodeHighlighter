package de.markusressel.kodehighlighter.language.kotlin.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class VisibilityKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): Sequence<MatchResult> {
        return PATTERN.findAll(spannable)
    }

    companion object {
        val PATTERN = "public(?=\\s)|protected(?=\\s)|private(?=\\s)".toRegex()
    }

}