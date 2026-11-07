package de.markusressel.kodehighlighter.language.generic

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.generic.rule.CommentRule
import de.markusressel.kodehighlighter.language.generic.rule.StringRule

/**
 * Comments and strings of [GenericRuleBook], in the forms most languages share
 */
internal object GenericRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "#", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            DelimitedRegion(StringRule, "'", escape = '\\'),
        )
    )

}
