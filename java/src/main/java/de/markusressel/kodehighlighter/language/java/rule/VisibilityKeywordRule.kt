package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule

class VisibilityKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, AnnotationRule.PATTERN)
    }

    companion object {
        val PATTERN = "public|protected|private".toRegex()
    }

}