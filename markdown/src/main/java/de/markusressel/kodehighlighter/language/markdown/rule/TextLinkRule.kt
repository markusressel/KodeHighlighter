package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class TextLinkRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "(?<!!)\\[.*?]\\(.*?\\)".toRegex()
    }

}