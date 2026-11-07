package de.markusressel.kodehighlighter.language.css

import de.markusressel.kodehighlighter.core.rule.RuleMatch

/**
 * Splits CSS at `{`, `}` and `;` into selectors, the text before a `{`, and the rest, like declarations and the
 * preludes of at-rules such as `@media (max-width: 600px)`. Rules match only in the sections they belong to, which
 * costs one pass over the text instead of a look ahead to the next `{` at every position.
 */
internal object CssSections {

    /**
     * @param text the text to search
     * @param regex the regex to match in the sections
     * @param selectors true to match in selectors, false to match in the rest
     * @param group the group of the regex to report, 0 for the whole match
     * @return all matches of the regex in the sections
     */
    fun findMatches(text: CharSequence, regex: Regex, selectors: Boolean, group: Int = 0): List<RuleMatch> {
        val matcher = regex.toPattern().matcher(text)
            .useTransparentBounds(true)
            .useAnchoringBounds(false)
        val matches = mutableListOf<RuleMatch>()
        var start = 0
        for (index in 0..text.length) {
            val char = if (index < text.length) text[index] else ';'
            if (char != '{' && char != '}' && char != ';') continue
            if ((char == '{' && !isAtRule(text, start, index)) == selectors && index > start) {
                matcher.region(start, index)
                while (matcher.find()) {
                    if (matcher.start(group) >= 0) matches += RuleMatch(matcher.start(group), matcher.end(group))
                }
            }
            start = index + 1
        }
        return matches
    }

    private fun isAtRule(text: CharSequence, start: Int, end: Int): Boolean {
        var index = start
        while (index < end && text[index].isWhitespace()) index++
        return index < end && text[index] == '@'
    }
}
