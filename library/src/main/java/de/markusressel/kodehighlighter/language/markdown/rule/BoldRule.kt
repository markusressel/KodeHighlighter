package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object BoldRule : LanguageRule {

    override val tokenType = TokenType.STRONG

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\\\*])\\*\\*(?=\\S)[^*\\n]*+(?:\\*(?!\\*)[^*\\n]*+)*+(?<=\\S)\\*\\*(?!\\*)|(?<![\\w_])__(?=\\S)[^_\\n]*+(?:_(?!_)[^_\\n]*+)*+(?<=\\S)__(?![\\w_])".toRegex()

}
