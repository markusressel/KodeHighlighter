package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object ItalicRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\\\*\\w])\\*(?![\\s*])[^*\\n]+?(?<![\\s*])\\*(?!\\*)|(?<![\\w_])_(?![\\s_])[^_\\n]+?(?<!\\s)_(?![\\w_])".toRegex()

}
