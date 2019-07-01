package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule

class CommentRule : SyntaxHighlighterRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, SINGLE_LINE_PATTERN) +
                RuleHelper.findRegexMatches(text, MULTI_LINE_PATTERN)
    }

    companion object {
        val MULTI_LINE_PATTERN = "('{3}|\"{3})[\\s\\S]*?\\1".toRegex(RegexOption.MULTILINE)
        val SINGLE_LINE_PATTERN = "\\s*#.*".toRegex(RegexOption.MULTILINE)
    }

}