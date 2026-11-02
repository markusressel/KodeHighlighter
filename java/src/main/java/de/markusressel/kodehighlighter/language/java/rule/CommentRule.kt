package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.language.java.JavaRegions

object CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return JavaRegions.scanner.findMatches(text, this)
    }

}
