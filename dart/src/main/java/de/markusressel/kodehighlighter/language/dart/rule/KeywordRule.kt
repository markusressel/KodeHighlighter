package de.markusressel.kodehighlighter.language.dart.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeywordRule : LanguageRule {

    override val tokenType = TokenType.KEYWORD

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:abstract|as|assert|async|await|base|break|case|catch|class|const|continue|covariant|default|deferred|do|else|enum|export|extends|extension|external|factory|final|finally|for|Function|if|implements|import|in|interface|is|late|library|mixin|new|operator|part|required|rethrow|return|sealed|static|super|switch|sync|this|throw|try|typedef|var|void|while|with|yield)\\b|\\b(?:get|set|of|on|show|hide|when)(?=\\s+\\w)".toRegex()

}
