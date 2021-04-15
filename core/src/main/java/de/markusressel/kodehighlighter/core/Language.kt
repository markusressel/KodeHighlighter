package de.markusressel.kodehighlighter.core

import java.util.regex.Pattern

/**
 * Interface to specify metadata about specific languages
 */
interface Language {

    /**
     * The name of this language in its most commonly used format
     */
    val name: String

    /**
     * A regex pattern that matches file endings (without the dot) associated with this language
     */
    val fileExtensionPattern: Pattern

}