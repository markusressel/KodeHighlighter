package de.markusressel.kodehighlighter.language.c

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.c.rule.CommentRule
import de.markusressel.kodehighlighter.language.c.rule.StringRule

/**
 * Regions of C whose content is opaque to other rules
 */
internal object CRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            PatternRegion(StringRule, "'", "'(?:[^'\\\\\\n]|\\\\.[^'\\n]{0,8})'".toRegex()),
        )
    )

}
