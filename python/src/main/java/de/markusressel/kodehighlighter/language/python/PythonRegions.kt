package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.python.rule.CommentRule
import de.markusressel.kodehighlighter.language.python.rule.StringRule

/**
 * Comments and strings of Python, including prefixes like `rb` and triple quotes
 */
internal object PythonRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "#", null),
            PatternRegion(StringRule, "\"'rRbBuUfF", "(?i)(?:(?<!\\w)(?:[rbuf]|br|rb|fr|rf))?(?:\"\"\"[\\s\\S]*?(?:\"\"\"|\\z)|'''[\\s\\S]*?(?:'''|\\z)|\"[^\"\\\\\\n]*+(?:\\\\[\\s\\S][^\"\\\\\\n]*+)*+\"?|'[^'\\\\\\n]*+(?:\\\\[\\s\\S][^'\\\\\\n]*+)*+'?)".toRegex()),
        )
    )

}
