package de.markusressel.kodehighlighter.language.go

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Go, the language of [GoRuleBook]
 */
object GoLanguage : Language(
    id = "go",
    displayName = "Go",
    fileExtensions = setOf("go"),
    mimeTypes = setOf("text/x-go"),
    aliases = setOf("golang"),
)
