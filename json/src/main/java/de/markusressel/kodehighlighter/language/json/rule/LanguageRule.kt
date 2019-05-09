package de.markusressel.kodehighlighter.language.json.rule

import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule

class LanguageRule : SyntaxHighlighterRule {

    override fun findMatches(spannable: CharSequence): List<MatchResult> {
        return PATTERN.findAll(spannable).toList()
    }

    companion object {
        val PATTERN = "\\b(true|false|null)\\b".toRegex()
    }

}