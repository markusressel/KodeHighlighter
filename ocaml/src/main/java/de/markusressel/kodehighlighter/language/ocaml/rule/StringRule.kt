package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.language.ocaml.OCamlRegions

object StringRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return OCamlRegions.scanner.findMatches(text, this)
    }

}
