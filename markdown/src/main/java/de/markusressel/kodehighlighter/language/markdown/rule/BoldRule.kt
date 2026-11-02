package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object BoldRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\\\*])\\*\\*(?=\\S)[^*\\n]*+(?:\\*(?!\\*)[^*\\n]*+)*+(?<=\\S)\\*\\*(?!\\*)|(?<![\\w_])__(?=\\S)[^_\\n]*+(?:_(?!_)[^_\\n]*+)*+(?<=\\S)__(?![\\w_])".toRegex()

}
