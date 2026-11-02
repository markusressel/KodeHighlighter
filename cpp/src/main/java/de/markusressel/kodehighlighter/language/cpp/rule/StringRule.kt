package de.markusressel.kodehighlighter.language.cpp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.cpp.CppRegions

object StringRule : LanguageRule {

    override val tokenType = TokenType.STRING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CppRegions.scanner.findMatches(text, this)
    }

}
