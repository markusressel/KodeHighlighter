package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

/**
 * Nested comments, strings, quoted strings and char literals of OCaml
 */
internal object OCamlRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "(*", "*)", multiLine = true, nested = true),
            DelimitedRegion(StringRule, "\"", escape = '\\', multiLine = true),
            PatternRegion(StringRule, "{", "\\{([a-z_]*)\\|[\\s\\S]*?\\|\\1\\}".toRegex()),
            PatternRegion(StringRule, "'", "(?<![\\w'])'(?:[^'\\\\\\n]|\\\\(?:[\\\\\"'ntbr ]|\\d{3}|x[0-9a-fA-F]{2}|o[0-3][0-7]{2}))'".toRegex()),
        )
    )

}
