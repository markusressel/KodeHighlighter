package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class CodeInlineRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        // TODO: This seems to be very inefficient, maybe there is a better way to detect such strings
        val PATTERN = "(`{1,3})([^`]+?)\\1".toRegex()
    }

}