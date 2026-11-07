package de.markusressel.kodehighlighter.language.csharp

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.csharp.rule.CommentRule
import de.markusressel.kodehighlighter.language.csharp.rule.StringRule

/**
 * Regions of C# whose content is opaque to other rules
 */
internal object CSharpRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"\"\"", multiLine = true),
            PatternRegion(StringRule, "@\$", "(?:\\\$@|@\\\$|@)\\\"[^\\\"]*+(?:\\\"\\\"[^\\\"]*+)*+\\\"?".toRegex()),
            DelimitedRegion(StringRule, "\$\"", "\"", escape = '\\'),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            PatternRegion(StringRule, "'", "'(?:[^'\\\\\\n]|\\\\.[^'\\n]{0,8})'".toRegex()),
        )
    )

}
