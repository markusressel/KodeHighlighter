package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class StrikeRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(~{2})([^~]+?)\\1".toRegex()
    }

}