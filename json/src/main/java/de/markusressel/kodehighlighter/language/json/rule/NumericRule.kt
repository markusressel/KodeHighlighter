package de.markusressel.kodehighlighter.language.json.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object NumericRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    /**
     * This pattern strictly follows the JSON number format (RFC 8259).
     * It excludes non-standard values like NaN, Infinity, and hex literals.
     * Breakdown:
     * -?                 // optional minus sign
     * (?:0|[1-9]\d*)     // integer part (zero, or a number not starting with zero)
     * (?:\.\d+)?         // optional fractional part
     * (?:[eE][+\-]?\d+)? // optional exponent part
     */
    private val PATTERN = "\\b-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+\\-]?\\d+)?\\b".toRegex()

}