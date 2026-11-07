package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.StringRule

/**
 * Comments, strings, text blocks and char literals of Java
 */
internal object JavaRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"\"\"", escape = '\\', multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            PatternRegion(StringRule, "'", "'(?:[^'\\\\\\n]|\\\\(?:u+[0-9a-fA-F]{4}|[0-7]{1,3}|.))'".toRegex()),
        )
    )

}
