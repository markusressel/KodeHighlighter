package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.Language
import java.util.regex.Pattern

object PythonLanguage : Language {
    override val name = "Python"
    override val fileExtensionPattern: Pattern = Pattern.compile("^py$")
}