package de.markusressel.kodehighlighter.language.dart

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.dart.rule.CommentRule
import de.markusressel.kodehighlighter.language.dart.rule.StringRule

/**
 * Regions of Dart whose content is opaque to other rules
 */
internal object DartRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true, nested = true),
            DelimitedRegion(StringRule, "r'''", "'''", multiLine = true, wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "r\"\"\"", "\"\"\"", multiLine = true, wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "r'", "'", wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "r\"", "\"", wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "'''", escape = '\\', multiLine = true),
            DelimitedRegion(StringRule, "\"\"\"", escape = '\\', multiLine = true),
            DelimitedRegion(StringRule, "'", escape = '\\'),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
        )
    )

}
