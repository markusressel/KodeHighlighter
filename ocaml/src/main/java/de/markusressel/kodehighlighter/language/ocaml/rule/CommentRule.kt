package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object  CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(\\(\\*)(.*?)(\\*\\))".toRegex(RegexOption.DOT_MATCHES_ALL)

}