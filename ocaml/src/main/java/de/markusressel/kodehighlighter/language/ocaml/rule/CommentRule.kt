package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

class CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    override fun getApplyingOrder(): Int {
        return 2
    }

    companion object {
        val PATTERN = "(\\(\\*)(.*?)(\\*\\))".toRegex(RegexOption.DOT_MATCHES_ALL)
    }

}