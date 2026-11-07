package de.markusressel.kodehighlighter.language.dart

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Dart, the language of [DartRuleBook]
 */
object DartLanguage : Language(
    id = "dart",
    displayName = "Dart",
    fileExtensions = setOf("dart"),
    mimeTypes = setOf("application/dart", "text/x-dart"),
    interpreters = setOf("dart"),
)
