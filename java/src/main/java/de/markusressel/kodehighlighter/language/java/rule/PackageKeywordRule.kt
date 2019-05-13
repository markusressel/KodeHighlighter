package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule

class PackageKeywordRule : SyntaxHighlighterRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, AnnotationRule.PATTERN)
    }

    companion object {
        val PATTERN = "package(?=\\s)".toRegex()
    }

}