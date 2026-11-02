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

}
