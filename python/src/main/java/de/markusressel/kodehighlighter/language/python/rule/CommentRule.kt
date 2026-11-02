package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.language.python.PythonRegions

object CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return PythonRegions.scanner.findMatches(text, this)
    }

}
