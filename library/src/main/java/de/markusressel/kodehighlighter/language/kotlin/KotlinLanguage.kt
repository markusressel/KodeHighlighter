package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Kotlin, the language of [KotlinRuleBook]
 */
object KotlinLanguage : Language(
    id = "kotlin",
    displayName = "Kotlin",
    fileExtensions = setOf("kt", "kts"),
    mimeTypes = setOf("text/x-kotlin"),
    aliases = setOf("kt", "kts"),
    interpreters = setOf("kotlin", "kotlinc"),
)
