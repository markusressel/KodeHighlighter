package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class ClassKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(class|object)(?=\\s)".toRegex()
    }

}