package de.markusressel.kodehighlighter.language.yaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object AnchorRule : LanguageRule {

    override val tokenType = TokenType.META

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<!\\S)[&*][A-Za-z0-9_-]+|(?<!\\S)!!?[\\w-]*".toRegex()

}
