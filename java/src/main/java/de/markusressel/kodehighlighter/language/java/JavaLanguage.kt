package de.markusressel.kodehighlighter.language.java

import de.markusressel.kodehighlighter.core.Language
import java.util.regex.Pattern

object JavaLanguage : Language {
    override val name = "Java"
    override val fileExtensionPattern: Pattern = Pattern.compile("^java?$")
}