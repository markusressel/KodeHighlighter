package de.markusressel.kodehighlighter.core.region

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule

/**
 * Describes a kind of text region, like a comment or a string, whose content is opaque to other rules.
 */
sealed interface RegionDefinition {

    /**
     * The rule that is reported for this region
     */
    val rule: LanguageRule

    /**
     * All ASCII characters a region of this kind can start with
     */
    val triggers: String
}

/**
 * A region with fixed start and end delimiters.
 *
 * A region that is not closed still counts: it ends at the end of the line, or at the end of the
 * text if [multiLine] is set. This keeps highlighting stable while typing.
 *
 * @param rule the rule that is reported for this region
 * @param start the opening delimiter
 * @param end the closing delimiter, null for a region that ends at the end of the line
 * @param escape the character that makes the following character literal, null if there is none
 * @param multiLine whether the region can span multiple lines
 * @param nested whether a nested [start] needs its own [end]
 * @param wordBoundaryBefore whether [start] must not directly follow a letter, digit or underscore
 * @param followedBy alternative rules, by the next non-whitespace character after the region
 */
class DelimitedRegion(
    override val rule: LanguageRule,
    val start: String,
    val end: String? = start,
    val escape: Char? = null,
    val multiLine: Boolean = false,
    val nested: Boolean = false,
    val wordBoundaryBefore: Boolean = false,
    val followedBy: Map<Char, LanguageRule> = emptyMap(),
) : RegionDefinition {

    init {
        require(start.isNotEmpty()) { "start must not be empty" }
        require(end == null || end.isNotEmpty()) { "end must not be empty" }
    }

    override val triggers: String = start.take(1)
}

/**
 * A region that needs a regex, for example because its closing delimiter depends on its opening delimiter.
 *
 * The regex can look at the text around the current position, so lookbehinds and `^` work as usual.
 * It must not need more than linear time, because it can be tried at every trigger character.
 *
 * @param rule the rule that is reported for this region
 * @param triggers all ASCII characters a region of this kind can start with
 * @param regex the regex that has to match at the start of the region
 * @param embedding content of the region in another language, null if the whole region gets the [rule]
 */
class PatternRegion(
    override val rule: LanguageRule,
    override val triggers: String,
    val regex: Regex,
    val embedding: Embedding? = null,
) : RegionDefinition

/**
 * Content of a [PatternRegion] in another language, like the YAML front matter or a fenced code block of Markdown.
 * A [RegionRuleBook] highlights the content with the rule book of that language. The rest of the region, like its
 * delimiters, keeps the rule of the region, and no other rule of the outer language matches anywhere in it.
 *
 * @param contentGroup the group of the regex that matches the content
 * @param ruleBook the rule book for the content, from the groups of the match, where group 0 is the whole region.
 * Null if the content is not highlighted, then the whole region gets the rule of the region. It is called for every
 * region and every highlighting, so it should return rule books it created before
 */
class Embedding(
    val contentGroup: Int,
    val ruleBook: (groups: List<String?>) -> LanguageRuleBook?,
)
