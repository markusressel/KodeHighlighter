package de.markusressel.kodehighlighter.language.markdown.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class TextLinkRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(?<!!)\\[.*?]\\(.*?\\)".toRegex()
    }

}