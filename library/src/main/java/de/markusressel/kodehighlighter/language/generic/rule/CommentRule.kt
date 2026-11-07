package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.generic.GenericRegions

object CommentRule : LanguageRule {

    override val tokenType = TokenType.COMMENT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return GenericRegions.scanner.findMatches(text, this)
    }

}
