package de.markusressel.kodehighlighter.language.json.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.language.json.JsonRegions

object CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return JsonRegions.scanner.findMatches(text, this)
    }

}
