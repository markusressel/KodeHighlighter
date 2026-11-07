package de.markusressel.kodehighlighter.language.dart.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.dart.DartRegions

object CommentRule : LanguageRule {

    override val tokenType = TokenType.COMMENT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return DartRegions.scanner.findMatches(text, this)
    }

}
