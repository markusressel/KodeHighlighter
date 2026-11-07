package de.markusressel.kodehighlighter.language.swift

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.swift.rule.CommentRule
import de.markusressel.kodehighlighter.language.swift.rule.StringRule

/**
 * Regions of Swift whose content is opaque to other rules
 */
internal object SwiftRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true, nested = true),
            PatternRegion(StringRule, "#", "(?<!#)(#+)\\\"[\\s\\S]*?(?:\\\"\\1|\\z)".toRegex()),
            DelimitedRegion(StringRule, "\"\"\"", escape = '\\', multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
        )
    )

}
