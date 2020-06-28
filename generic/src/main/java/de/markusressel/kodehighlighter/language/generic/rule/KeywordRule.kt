package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

class KeywordRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN) + RuleHelper.findRegexMatches(text, DOT_PATTERN) + RuleHelper.findRegexMatches(text, OPERATOR_PATTERN)
    }

    companion object {
        val OPERATOR_PATTERN = "[=+.]".toRegex()
        val DOT_PATTERN = "(\\(|\\s|\\[|=|:|\\+|\\.|\\{|,)((['\"])([^\\\\1]|\\\\.)*?(\\3))".toRegex(RegexOption.MULTILINE)
        val PATTERN = "\\b(and|array|as|b(ool(ean)?|reak)|c(ase|atch|har|lass|on(st|tinue))|d(ef|elete|o(uble)?)|e(cho|lse(if)?|xit|xtends|xcept)|f(inally|loat|or(each)?|unction)|global|if|import|int(eger)?|long|new|object|or|pr(int|ivate|otected)|public|return|self|st(ring|ruct|atic)|switch|th(en|is|row)|try|(un)?signed|var|void|while)(?=\\b)".toRegex(RegexOption.IGNORE_CASE)
    }

}