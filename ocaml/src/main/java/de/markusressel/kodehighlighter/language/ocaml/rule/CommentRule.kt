package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.ocaml.OCamlRegions

object CommentRule : LanguageRule {

    override val tokenType = TokenType.COMMENT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return OCamlRegions.scanner.findMatches(text, this)
    }

}
