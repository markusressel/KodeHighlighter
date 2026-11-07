package de.markusressel.kodehighlighter.language.swift.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.swift.SwiftRegions

object CommentRule : LanguageRule {

    override val tokenType = TokenType.COMMENT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return SwiftRegions.scanner.findMatches(text, this)
    }

}
