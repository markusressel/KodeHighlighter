package de.markusressel.kodehighlighter.language.toml

import de.markusressel.kodehighlighter.core.language.Language

/**
 * TOML, the language of [TomlRuleBook]
 */
object TomlLanguage : Language(
    id = "toml",
    displayName = "TOML",
    fileExtensions = setOf("toml"),
    fileNames = setOf("Cargo.lock", "Pipfile", "poetry.lock", "uv.lock", "pdm.lock"),
    mimeTypes = setOf("application/toml"),
)
