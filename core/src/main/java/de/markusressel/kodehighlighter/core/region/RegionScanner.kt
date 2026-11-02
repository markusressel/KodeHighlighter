package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import java.util.regex.Matcher

/**
 * A text passage found by a [RegionScanner]
 *
 * @param rule the rule of the region
 * @param startIndex the start of the region
 * @param endIndex the end of the region (exclusive)
 */
data class Region(val rule: LanguageRule, val startIndex: Int, val endIndex: Int)

/**
 * Finds regions like comments and strings in a single pass from start to end, so that the start of a
 * string inside of a comment, or of a comment inside of a string, is not mistaken for one.
 *
 * The scanner itself needs linear time and does not recurse. It is safe to use from multiple threads.
 *
 * @param definitions the kinds of regions, earlier ones win if multiple match at the same position,
 * so `"""` has to be defined before `"`
 */
class RegionScanner(definitions: List<RegionDefinition>) {

    private val definitionsByTrigger = arrayOfNulls<MutableList<RegionDefinition>>(ASCII_SIZE)

    /**
     * All rules this scanner can report
     */
    val rules: Set<LanguageRule> = definitions.flatMapTo(mutableSetOf()) { definition ->
        when (definition) {
            is DelimitedRegion -> definition.followedBy.values + definition.rule
            is PatternRegion -> listOf(definition.rule)
        }
    }

    init {
        definitions.forEach { definition ->
            definition.triggers.forEach { trigger ->
                require(trigger.code < ASCII_SIZE) { "Only ASCII triggers are supported: $trigger" }
                val candidates = definitionsByTrigger[trigger.code]
                    ?: mutableListOf<RegionDefinition>().also { definitionsByTrigger[trigger.code] = it }
                candidates += definition
            }
        }
    }

    /**
     * Finds all regions in the given text
     *
     * @param text the text to scan
     * @return the regions, ordered by position and without overlaps
     */
    fun scan(text: CharSequence): List<Region> {
        val string = text.toString()
        // one matcher per definition, since every new matcher hands the whole text to the native regex engine
        val matchers = mutableMapOf<PatternRegion, Matcher>()
        val regions = mutableListOf<Region>()

        var index = 0
        while (index < string.length) {
            val region = definitionsAt(string[index])?.firstNotNullOfOrNull { definition ->
                when (definition) {
                    is DelimitedRegion -> matchDelimited(definition, string, index)
                    is PatternRegion -> matchPattern(definition, string, index, matchers)
                }
            }
            if (region == null) {
                index++
            } else {
                regions += region
                index = region.endIndex
            }
        }
        return regions
    }

    /**
     * Finds the regions of a single rule in the given text
     *
     * @param text the text to scan
     * @param rule the rule to find regions of
     * @return the matches, ordered by position
     */
    fun findMatches(text: CharSequence, rule: LanguageRule): List<RuleMatch> {
        return scan(text)
            .filter { it.rule === rule }
            .map { RuleMatch(it.startIndex, it.endIndex) }
    }

    private fun definitionsAt(char: Char): List<RegionDefinition>? {
        return if (char.code < ASCII_SIZE) definitionsByTrigger[char.code] else null
    }

    private fun matchPattern(
        definition: PatternRegion,
        text: String,
        start: Int,
        matchers: MutableMap<PatternRegion, Matcher>,
    ): Region? {
        val matcher = matchers.getOrPut(definition) {
            definition.regex.toPattern().matcher(text)
                .useAnchoringBounds(false)
                .useTransparentBounds(true)
        }
        matcher.region(start, text.length)
        return if (matcher.lookingAt() && matcher.end() > start) {
            Region(definition.rule, start, matcher.end())
        } else {
            null
        }
    }

    private fun matchDelimited(definition: DelimitedRegion, text: String, start: Int): Region? {
        if (!text.startsWith(definition.start, start)) return null
        if (definition.wordBoundaryBefore && start > 0 && text[start - 1].isWordCharacter()) return null

        val end = findDelimitedEnd(definition, text, start)
        return Region(selectRule(definition, text, end), start, end)
    }

    private fun findDelimitedEnd(definition: DelimitedRegion, text: String, start: Int): Int {
        val closing = definition.end
        var depth = 1
        var index = start + definition.start.length
        while (index < text.length) {
            val char = text[index]
            when {
                char == definition.escape -> index += 2
                closing != null && text.startsWith(closing, index) -> {
                    index += closing.length
                    if (--depth == 0) return index
                }

                definition.nested && text.startsWith(definition.start, index) -> {
                    depth++
                    index += definition.start.length
                }

                char == '\n' && !definition.multiLine -> return index
                else -> index++
            }
        }
        return text.length
    }

    private fun selectRule(definition: DelimitedRegion, text: String, end: Int): LanguageRule {
        if (definition.followedBy.isEmpty()) return definition.rule

        var index = end
        while (index < text.length && text[index].isWhitespace()) index++
        return text.getOrNull(index)?.let(definition.followedBy::get) ?: definition.rule
    }

    private fun Char.isWordCharacter() = isLetterOrDigit() || this == '_'

    private companion object {
        const val ASCII_SIZE = 128
    }
}
