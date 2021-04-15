package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.Language
import java.util.regex.Pattern

object MarkdownLanguage : Language {
    override val name = "Markdown"
    override val fileExtensionPattern: Pattern = Pattern.compile("^md$")
}