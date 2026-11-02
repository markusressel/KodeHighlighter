package de.markusressel.kodehighlighter.language.cpp

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.cpp.rule.CommentRule
import de.markusressel.kodehighlighter.language.cpp.rule.StringRule

/**
 * Regions of C++ whose content is opaque to other rules
 */
internal object CppRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            PatternRegion(StringRule, "RuUL", "(?<!\\w)(?:u8|[uUL])?R\\\"([^()\\\\\\s]{0,16})\\([\\s\\S]*?(?:\\)\\1\\\"|\\z)".toRegex()),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            PatternRegion(StringRule, "'", "(?<![\\w'])'(?:[^'\\\\\\n]|\\\\.[^'\\n]{0,8})'".toRegex()),
        )
    )

}
