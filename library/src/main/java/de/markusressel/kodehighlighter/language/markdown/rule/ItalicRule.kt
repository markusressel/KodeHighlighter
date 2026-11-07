package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object ItalicRule : LanguageRule {

    override val tokenType = TokenType.EMPHASIS

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\\\*\\w])\\*(?![\\s*])[^*\\n]+?(?<![\\s*])\\*(?!\\*)|(?<![\\w_])_(?![\\s_])[^_\\n]+?(?<!\\s)_(?![\\w_])".toRegex()

}
