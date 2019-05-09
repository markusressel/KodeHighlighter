package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class CodeLineRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(?m)^ {4}.+".toRegex()
    }

}