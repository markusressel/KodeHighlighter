package de.markusressel.kodehighlighter.language.javascript

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.javascript.rule.CommentRule
import de.markusressel.kodehighlighter.language.javascript.rule.RegexRule
import de.markusressel.kodehighlighter.language.javascript.rule.StringRule

/**
 * Regions of JavaScript whose content is opaque to other rules
 */
internal object JavaScriptRegions {

    val scanner = RegionScanner(
        listOf(
            PatternRegion(CommentRule, "#", "\\A#![^\\n]*".toRegex()),
            DelimitedRegion(CommentRule, "//", null),
            DelimitedRegion(CommentRule, "/*", "*/", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\'),
            DelimitedRegion(StringRule, "'", escape = '\\'),
            DelimitedRegion(StringRule, "`", escape = '\\', multiLine = true),
            // only where an expression starts, otherwise a `/` divides
            PatternRegion(
                RegexRule,
                "/",
                ("(?m)(?<=(?:^|[(,=:\\[!&|?{};+\\-*%<>~^])[ \\t]{0,32}|\\b(?:return|typeof|case|do|else|in|of|new|" +
                    "delete|void|throw|yield|await|instanceof)[ \\t]{1,32})" +
                    "/(?![*/])(?:[^\\n\\[/\\\\]|\\\\.|\\[(?:[^\\n\\]\\\\]|\\\\.)*\\])+/[dgimsuyv]*").toRegex(),
            ),
        )
    )

}
