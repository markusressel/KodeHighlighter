package de.markusressel.kodehighlighter.language.kotlin.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.language.kotlin.KotlinRegions

object StringRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return KotlinRegions.scanner.findMatches(text, this)
    }

}
