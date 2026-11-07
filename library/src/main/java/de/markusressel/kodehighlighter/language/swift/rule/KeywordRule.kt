package de.markusressel.kodehighlighter.language.swift.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeywordRule : LanguageRule {

    override val tokenType = TokenType.KEYWORD

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:actor|any|as|associatedtype|async|await|break|case|catch|class|continue|default|defer|deinit|do|else|enum|extension|fallthrough|fileprivate|final|for|func|guard|if|import|in|init|inout|internal|is|let|nonisolated|operator|override|precedencegroup|private|protocol|public|repeat|rethrows|return|self|Self|some|static|struct|subscript|super|switch|throw|throws|try|typealias|var|where|while)\\b|\\bopen(?=\\s+[a-zA-Z])".toRegex()

}
