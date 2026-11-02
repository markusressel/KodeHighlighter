package de.markusressel.kodehighlighter.language.xml

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.xml.rule.CDataRule
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule

/**
 * Regions of XML whose content is opaque to other rules
 */
internal object XmlRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "<!--", "-->", multiLine = true),
            DelimitedRegion(CDataRule, "<![CDATA[", "]]>", multiLine = true),
        )
    )

}
