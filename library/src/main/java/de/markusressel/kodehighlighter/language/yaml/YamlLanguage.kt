package de.markusressel.kodehighlighter.language.yaml

import de.markusressel.kodehighlighter.core.language.Language

/**
 * YAML, the language of [YamlRuleBook]
 */
object YamlLanguage : Language(
    id = "yaml",
    displayName = "YAML",
    fileExtensions = setOf("yaml", "yml"),
    fileNames = setOf(".clang-format", ".clang-tidy", ".gemrc"),
    mimeTypes = setOf("application/yaml", "application/x-yaml", "text/yaml", "text/x-yaml"),
    aliases = setOf("yml"),
    firstLinePrefixes = listOf("%YAML"),
)
