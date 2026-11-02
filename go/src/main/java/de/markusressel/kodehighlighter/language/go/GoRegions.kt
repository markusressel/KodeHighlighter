package de.markusressel.kodehighlighter.language.go

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.go.rule.CommentRule
import de.markusressel.kodehighlighter.language.go.rule.StringRule

/**
 * Regions of Go whose content is opaque to other rules
 */
internal object GoRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            DelimitedRegion(StringRule, "`", multiLine = true),
            PatternRegion(StringRule, "'", "'(?:[^'\\\\\\n]|\\\\.[^'\\n]{0,8})'".toRegex()),
        )
    )

}
