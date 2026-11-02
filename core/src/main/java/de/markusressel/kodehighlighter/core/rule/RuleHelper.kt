package de.markusressel.kodehighlighter.core.rule

/**
 * Helper to implement [LanguageRule] more easily
 */
object RuleHelper {

    /**
     * Finds all matches for the given regex in the given text
     *
     * This uses one matcher instead of [Regex.findAll], which creates a new matcher for each match. On Android
     * every new matcher hands the whole text to the native regex engine, so the time grows with the
     * number of matches times the length of the text.
     *
     * @param text the text to use
     * @param regex the regex to use
     * @return list of matches
     */
    fun findRegexMatches(text: CharSequence, regex: Regex): List<RuleMatch> {
        val matcher = regex.toPattern().matcher(text)
        val matches = mutableListOf<RuleMatch>()
        while (matcher.find()) {
            matches += RuleMatch(matcher.start(), matcher.end())
        }
        return matches
    }

    /**
     * Finds all matches for the given regex in the given text and returns the range of a capture group.
     * Matches where the group did not participate are skipped.
     *
     * @param text the text to use
     * @param regex the regex to use
     * @param group the index of the capture group
     * @return list of matches
     */
    fun findRegexGroupMatches(text: CharSequence, regex: Regex, group: Int): List<RuleMatch> {
        val matcher = regex.toPattern().matcher(text)
        val matches = mutableListOf<RuleMatch>()
        while (matcher.find()) {
            if (matcher.start(group) >= 0) {
                matches += RuleMatch(matcher.start(group), matcher.end(group))
            }
        }
        return matches
    }

}
