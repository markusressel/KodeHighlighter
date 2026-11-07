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
 *
 * The content of a region with an [Embedding] is highlighted with the rule book of its language, whose matches come
 * after those of [getRules]. Only the rest of such a region, like its delimiters, gets the rule of the region.
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
            val own = getRules().map { rule ->
                async {
                    val matches = if (rule in scanner.rules) {
                        regions.filter { it.rule === rule }.flatMap { it.ownMatches() }
                    } else {
                        findMatchesOutsideOfRegions(rule, text, regions, regionStarts)
                    }
                    if (matches.isNotEmpty()) RuleMatches(rule, matches) else null
                }
            }
            val embedded = regions.mapNotNull { region ->
                val content = region.embedded ?: return@mapNotNull null
                async {
                    content.ruleBook.createHighlighting(text.substring(content.startIndex, content.endIndex))
                        .map { ruleMatches ->
                            ruleMatches.rule to ruleMatches.matches.map {
                                RuleMatch(it.startIndex + content.startIndex, it.endIndex + content.startIndex)
                            }
                        }
                }
            }
            merge(own.awaitAll().filterNotNull(), embedded.awaitAll().flatten())
        }
    }

    /** @return the parts of the region that get its rule, all of it unless it embeds another language */
    private fun Region.ownMatches(): List<RuleMatch> {
        val content = embedded ?: return listOf(RuleMatch(startIndex, endIndex))
        return listOf(RuleMatch(startIndex, content.startIndex), RuleMatch(content.endIndex, endIndex))
            .filter { it.endIndex > it.startIndex }
    }

    /** @return the matches of each rule together, a rule can match in the outer text and in embedded content */
    private fun merge(own: List<RuleMatches>, embedded: List<Pair<LanguageRule, List<RuleMatch>>>): List<RuleMatches> {
        if (embedded.isEmpty()) return own
        val byRule = LinkedHashMap<LanguageRule, MutableList<RuleMatch>>()
        own.forEach { byRule.getOrPut(it.rule) { mutableListOf() } += it.matches }
        embedded.forEach { (rule, matches) -> byRule.getOrPut(rule) { mutableListOf() } += matches }
        return byRule.map { (rule, matches) -> RuleMatches(rule, matches.sortedBy { it.startIndex }) }
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
