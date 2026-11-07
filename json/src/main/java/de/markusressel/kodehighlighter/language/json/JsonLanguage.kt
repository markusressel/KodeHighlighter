package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.language.Language

/**
 * JSON, the language of [JsonRuleBook]
 */
object JsonLanguage : Language(
    id = "json",
    displayName = "JSON",
    fileExtensions = setOf("json", "geojson", "webmanifest", "har", "jsonl", "ndjson"),
    fileNames = setOf(".babelrc", ".eslintrc", ".prettierrc", "composer.lock", "flake.lock"),
    mimeTypes = setOf("application/json", "application/geo+json", "application/manifest+json", "application/x-ndjson"),
    aliases = setOf("jsonl", "ndjson"),
)
