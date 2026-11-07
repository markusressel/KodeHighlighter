package de.markusressel.kodehighlighter.language.rust

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.rust.rule.CommentRule
import de.markusressel.kodehighlighter.language.rust.rule.StringRule

/**
 * Regions of Rust whose content is opaque to other rules
 */
internal object RustRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true, nested = true),
            PatternRegion(StringRule, "rbc", "(?<!\\w)[bc]?r(#*)\\\"[\\s\\S]*?(?:\\\"\\1|\\z)".toRegex()),
            DelimitedRegion(StringRule, "b\"", "\"", escape = '\\', multiLine = true, wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "c\"", "\"", escape = '\\', multiLine = true, wordBoundaryBefore = true),
            DelimitedRegion(StringRule, "\"", escape = '\\', multiLine = true),
            PatternRegion(StringRule, "'b", "(?<!\\w)b?'(?:[^'\\\\\\n]|\\\\(?:u\\{[0-9a-fA-F_]{1,8}\\}|x[0-9a-fA-F]{2}|.))'".toRegex()),
        )
    )

}
