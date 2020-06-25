package de.markusressel.kodehighlighter.core.rule

/**
 * Interface for a single highlighter rule.
 *
 * A rule is used to identify text passages that matches the rule.
 * The styles that are applied to those passages are defined in a color scheme.
 */
interface SyntaxHighlighterRule {

    /**
     * Find segments in the text that are affected by this rule
     *
     * @param text the text
     * @return a list of matching sections
     */
    fun findMatches(text: CharSequence): List<RuleMatch>

}