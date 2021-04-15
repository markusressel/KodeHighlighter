package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.Language
import java.util.regex.Pattern

object JsonLanguage : Language {
    override val name = "JSON"
    override val fileExtensionPattern: Pattern = Pattern.compile("^json$")
}