package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class ClassKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "class(?=\\s)".toRegex()
    }

}