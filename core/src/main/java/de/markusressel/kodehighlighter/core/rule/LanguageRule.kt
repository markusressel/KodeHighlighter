package de.markusressel.kodehighlighter.core.rule

/**
 * Interface for a single language rule.
 *
 * A rule is used to identify text passages that match characteristic words or tokens in a text.
 * The styles that are applied to those passages are defined in a [ColorScheme].
 */
interface LanguageRule {

    /**
     * Find segments in the text that are affected by this rule
     *
     * @param text the text
     * @return a list of matching sections
     */
    fun findMatches(text: CharSequence): List<RuleMatch>

    /**
     * Allow rules to be applied in a specific order
     * Rules are applied in an ascendant order (1, 2, ...)
     *
     * @return an integer determining the order of applying, defaults to 1
     */
    fun getApplyingOrder(): Int {
        return 1
    }

}