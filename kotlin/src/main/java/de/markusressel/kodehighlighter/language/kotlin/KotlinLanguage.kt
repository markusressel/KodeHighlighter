package de.markusressel.kodehighlighter.language.kotlin

import de.markusressel.kodehighlighter.core.Language
import java.util.regex.Pattern

object KotlinLanguage : Language {
    override val name = "Kotlin"
    override val fileExtensionPattern: Pattern = Pattern.compile("^kt$")
}