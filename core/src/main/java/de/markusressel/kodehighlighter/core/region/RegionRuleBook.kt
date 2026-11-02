package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.RuleMatches
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * A rule book that finds regions like comments and strings first, so that no other rule matches inside of them.
 *
 * [getRules] has to include the rules of the [scanner], its order defines the order of the result.
 * A match of another rule is dropped if it starts or ends inside of a region. It is kept if it fully
 * contains regions, for example bold Markdown text around inline code.
 */
abstract class RegionRuleBook : LanguageRuleBook {

    /**
     * The scanner to find regions with
     */
    protected abstract val scanner: RegionScanner

    override suspend fun createHighlighting(charSequence: CharSequence): List<RuleMatches> {
        val text = charSequence.toString()
        val regions = scanner.scan(text)
        val regionStarts = IntArray(regions.size) { regions[it].startIndex }

        return coroutineScope {
            getRules().map { rule ->
                async {
                    val matches = if (rule in scanner.rules) {
                        regions.filter { it.rule === rule }.map { RuleMatch(it.startIndex, it.endIndex) }
                    } else {
                        findMatchesOutsideOfRegions(rule, text, regions, regionStarts)
                    }
                    if (matches.isNotEmpty()) RuleMatches(rule, matches) else null
                }
            }.awaitAll().filterNotNull()
        }
    }

    private fun findMatchesOutsideOfRegions(
        rule: LanguageRule,
        text: String,
        regions: List<Region>,
        regionStarts: IntArray,
    ): List<RuleMatch> {
        return rule.findMatches(text).filterNot { match ->
            val regionAtStart = regionContaining(match.startIndex, regions, regionStarts)
            val regionAtEnd = regionContaining(match.endIndex - 1, regions, regionStarts)
            regionAtStart != null || (regionAtEnd != null && regionAtEnd.endIndex > match.endIndex)
        }
    }

    private fun regionContaining(index: Int, regions: List<Region>, regionStarts: IntArray): Region? {
        val position = regionStarts.binarySearch(index)
        val candidate = regions.getOrNull(if (position >= 0) position else -position - 2)
        return candidate?.takeIf { index < it.endIndex }
    }
}
