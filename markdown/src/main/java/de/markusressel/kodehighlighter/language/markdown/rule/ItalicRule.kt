package de.markusressel.kodehighlighter.language.markdown.rule

import android.text.Spannable
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class ItalicRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: Spannable): Sequence<MatchResult> {
        return PATTERN.findAll(spannable)
    }

    companion object {
        val PATTERN = "(?<!\\*)\\*(?!\\*).+?\\*(?!\\*)".toRegex()
    }

}