package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.kotlin.rule.CommentRule
import de.markusressel.kodehighlighter.language.kotlin.rule.StringRule

/**
 * Comments, strings and char literals of Kotlin
 */
internal object KotlinRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true, nested = true),
            PatternRegion(StringRule, "\"", "\"\"\"[\\s\\S]*?(?:\"\"\"(?!\")|\\z)".toRegex()),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            PatternRegion(StringRule, "'", "'(?:[^'\\\\\\n]|\\\\(?:u+[0-9a-fA-F]{4}|[0-7]{1,3}|.))'".toRegex()),
        )
    )

}
