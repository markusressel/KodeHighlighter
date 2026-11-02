package de.markusressel.kodehighlighter.language.toml

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.toml.rule.CommentRule
import de.markusressel.kodehighlighter.language.toml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.toml.rule.StringRule

/**
 * Regions of TOML whose content is opaque to other rules
 */
internal object TomlRegions {

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "#", null),
            DelimitedRegion(StringRule, "\"\"\"", escape = '\\', multiLine = true),
            DelimitedRegion(StringRule, "'''", multiLine = true),
            DelimitedRegion(StringRule, "\"", escape = '\\', followedBy = mapOf('=' to QuotedKeyRule)),
            DelimitedRegion(StringRule, "'", followedBy = mapOf('=' to QuotedKeyRule)),
        )
    )

}
