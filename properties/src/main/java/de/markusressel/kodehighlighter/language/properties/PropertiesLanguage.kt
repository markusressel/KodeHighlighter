package de.markusressel.kodehighlighter.language.properties

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Java Properties, the language of [PropertiesRuleBook]
 */
object PropertiesLanguage : Language(
    id = "properties",
    displayName = "Java Properties",
    fileExtensions = setOf("properties"),
    mimeTypes = setOf("text/x-java-properties"),
    aliases = setOf("jproperties", "java-properties"),
)
