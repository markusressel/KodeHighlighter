package de.markusressel.kodehighlighter.core.rule

/**
 * Interface for a single language rule.
 *
 * A rule is used to identify text passages that match characteristic words or tokens in a text.
 * The styles that are applied to those passages are defined in a [ColorScheme].
 */
interface LanguageRule {

    /**
     * The meaning of the text this rule matches, used by a [TokenTheme]. Null if there is none,
     * in which case a [TokenTheme] does not style the matches.
     */
    val tokenType: TokenType?
        get() = null

    /**
     * Find segments in the text that are affected by this rule
     *
     * @param text the text
     * @return a list of matching sections
     */
    fun findMatches(text: CharSequence): List<RuleMatch>

}