package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class ExceptRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(except) (\\w+):".toRegex()
    }

}