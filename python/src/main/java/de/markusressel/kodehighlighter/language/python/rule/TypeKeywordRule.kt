package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class TypeKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "None|True|False|NotImplemented|\\.\\.\\.".toRegex()
    }

}