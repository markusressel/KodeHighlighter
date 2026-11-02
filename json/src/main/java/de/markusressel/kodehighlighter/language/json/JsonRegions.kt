package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.json.rule.CommentRule
import de.markusressel.kodehighlighter.language.json.rule.PropertyRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * Strings, property names and the comments of JSONC
 */
internal object JsonRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(StringRule, "\"", escape = '\\', followedBy = mapOf(':' to PropertyRule)),
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
        )
    )

}
