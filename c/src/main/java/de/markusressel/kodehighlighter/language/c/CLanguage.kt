package de.markusressel.kodehighlighter.language.c

import de.markusressel.kodehighlighter.core.language.Language

/**
 * C, the language of [CRuleBook]
 */
object CLanguage : Language(
    id = "c",
    displayName = "C",
    fileExtensions = setOf("c", "h"),
    mimeTypes = setOf("text/x-c", "text/x-csrc", "text/x-chdr"),
)
