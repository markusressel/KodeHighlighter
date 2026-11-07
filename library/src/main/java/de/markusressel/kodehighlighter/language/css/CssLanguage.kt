package de.markusressel.kodehighlighter.language.css

import de.markusressel.kodehighlighter.core.language.Language

/**
 * CSS, the language of [CssRuleBook]
 */
object CssLanguage : Language(
    id = "css",
    displayName = "CSS",
    fileExtensions = setOf("css"),
    mimeTypes = setOf("text/css"),
)
