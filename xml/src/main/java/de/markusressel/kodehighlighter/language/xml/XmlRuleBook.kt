package de.markusressel.kodehighlighter.language.xml

import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeValueRule
import de.markusressel.kodehighlighter.language.xml.rule.CDataRule
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule
import de.markusressel.kodehighlighter.language.xml.rule.DoctypeRule
import de.markusressel.kodehighlighter.language.xml.rule.EntityRule
import de.markusressel.kodehighlighter.language.xml.rule.TagRule

/**
 * XML and HTML rule book, without embedded languages like JavaScript
 */
open class XmlRuleBook : RegionRuleBook() {

    override val scanner = XmlRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        TagRule,
        DoctypeRule,
        AttributeRule,
        AttributeValueRule,
        EntityRule,
        CommentRule,
        CDataRule,
    )

    override fun getRules() = rules

}
