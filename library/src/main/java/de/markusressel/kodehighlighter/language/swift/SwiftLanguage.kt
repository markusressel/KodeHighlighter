package de.markusressel.kodehighlighter.language.swift

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Swift, the language of [SwiftRuleBook]
 */
object SwiftLanguage : Language(
    id = "swift",
    displayName = "Swift",
    fileExtensions = setOf("swift"),
    mimeTypes = setOf("text/x-swift"),
    interpreters = setOf("swift"),
)
