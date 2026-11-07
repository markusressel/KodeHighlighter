package de.markusressel.kodehighlighter.language.yaml

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.yaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.yaml.rule.QuotedKeyRule
import de.markusressel.kodehighlighter.language.yaml.rule.StringRule

/**
 * Regions of YAML whose content is opaque to other rules
 */
internal object YamlRegions {

    val scanner = RegionScanner(
        listOf(
            PatternRegion(CommentRule, "#", "(?<!\\S)#[^\\n]*".toRegex()),
            DelimitedRegion(StringRule, "\"", escape = '\\', multiLine = true, followedBy = mapOf(':' to QuotedKeyRule)),
            PatternRegion(StringRule, "'", "(?<![\\w])'[^']*+(?:''[^']*+)*+'?".toRegex()),
        )
    )

}
