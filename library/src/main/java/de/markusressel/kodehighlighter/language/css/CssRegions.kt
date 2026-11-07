package de.markusressel.kodehighlighter.language.css

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.css.rule.CommentRule
import de.markusressel.kodehighlighter.language.css.rule.StringRule

/**
 * Regions of CSS whose content is opaque to other rules
 */
internal object CssRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            DelimitedRegion(StringRule, "'", escape = '\\'),
            // an unquoted URL, which can contain `//` and `/*`
            PatternRegion(StringRule, "(", "(?i)(?<=\\burl)\\((?![ \\t]*['\"])[^)\\n]*\\)?".toRegex()),
        )
    )

}
