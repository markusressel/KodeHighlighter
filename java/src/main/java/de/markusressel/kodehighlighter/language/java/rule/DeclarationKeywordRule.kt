package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object DeclarationKeywordRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:interface|enum|extends|implements|throws|instanceof|new|this|super)\\b|\\brecord(?=\\s+[A-Z])|\\bpermits(?=\\s+[A-Z])|\\bvar(?=\\s+\\w)".toRegex()

}
