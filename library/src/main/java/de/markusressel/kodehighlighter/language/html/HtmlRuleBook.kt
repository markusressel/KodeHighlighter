package de.markusressel.kodehighlighter.language.html

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.html.rule.EmbeddingTagRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeValueRule
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule
import de.markusressel.kodehighlighter.language.xml.rule.DoctypeRule
import de.markusressel.kodehighlighter.language.xml.rule.EntityRule
import de.markusressel.kodehighlighter.language.xml.rule.TagRule

/**
 * HTML rule book, with the content of `script` elements highlighted as JavaScript and of `style` elements as CSS.
 * Tags, attributes and entities use the rules of XML.
 */
open class HtmlRuleBook : RegionRuleBook() {

    override val language: Language get() = HtmlLanguage

    override val scanner = HtmlRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        TagRule,
        EmbeddingTagRule,
        DoctypeRule,
        AttributeRule,
        AttributeValueRule,
        EntityRule,
        CommentRule,
    )

    override fun getRules() = rules

}
