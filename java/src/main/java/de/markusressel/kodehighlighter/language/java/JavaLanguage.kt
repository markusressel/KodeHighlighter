package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Java, the language of [JavaRuleBook]
 */
object JavaLanguage : Language(
    id = "java",
    displayName = "Java",
    fileExtensions = setOf("java"),
    mimeTypes = setOf("text/x-java", "text/x-java-source"),
    interpreters = setOf("java"),
)
