package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object  KeywordRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(and|as|assert|asr|begin|class|constraint|do|done|downto|else|end|exception|external|for|fun|function|functor|if|in|include|inherit!|inherit|initializer|land|lazy|let|lor|lsl|lsr|lxor|match|method!|method|mod|module|mutable|new|object|of|open!|open|or|private|rec|sig|struct|then|to|try|type|val!|val|virtual|when|while|with|parser|value)(?=\\b)".toRegex()

}