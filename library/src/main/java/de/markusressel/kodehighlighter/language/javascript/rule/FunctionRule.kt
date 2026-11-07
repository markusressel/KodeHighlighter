package de.markusressel.kodehighlighter.language.javascript.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

/**
 * Names of declared functions and methods
 */
object FunctionRule : LanguageRule {

    override val tokenType = TokenType.FUNCTION

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        // each alternative of the pattern has its own group
        val matcher = PATTERN.toPattern().matcher(text)
        val matches = mutableListOf<RuleMatch>()
        while (matcher.find()) {
            val group = (1..matcher.groupCount()).first { matcher.start(it) >= 0 }
            matches += RuleMatch(matcher.start(group), matcher.end(group))
        }
        return matches
    }

    private val PATTERN = "(?m)\\bfunction\\b[ \\t]*\\*?[ \\t]*([A-Za-z_\$][\\w\$]*)|^[ \\t]*+(?:(?:async|static|get|set)[ \\t]++)*+(?:\\*[ \\t]*+)?(?!(?:if|for|while|switch|catch|with|return|function)(?![\\w\$]))([A-Za-z_\$][\\w\$]*)(?=[ \\t]*\\([^()\\n]*\\)[ \\t]*\\{)|(?<![\\w\$.])([A-Za-z_\$][\\w\$]*)(?=[ \\t]*=[ \\t]*(?:async[ \\t]*)?(?:function\\b|\\([^()\\n]*\\)[ \\t]*=>|[A-Za-z_\$][\\w\$]*[ \\t]*=>))".toRegex()

}
