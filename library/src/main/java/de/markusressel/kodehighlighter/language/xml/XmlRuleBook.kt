package de.markusressel.kodehighlighter.language.xml

import de.markusressel.kodehighlighter.core.language.Language
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
 * XML rule book. [de.markusressel.kodehighlighter.language.html.HtmlRuleBook] highlights HTML, with its JavaScript and CSS
 */
open class XmlRuleBook : RegionRuleBook() {

    override val language: Language get() = XmlLanguage

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
