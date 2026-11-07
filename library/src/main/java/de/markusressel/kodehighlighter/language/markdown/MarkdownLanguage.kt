package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Markdown, the language of [MarkdownRuleBook]
 */
object MarkdownLanguage : Language(
    id = "markdown",
    displayName = "Markdown",
    fileExtensions = setOf("md", "markdown", "mdown", "mkd", "mkdn", "mdwn"),
    mimeTypes = setOf("text/markdown", "text/x-markdown"),
    aliases = setOf("md", "gfm"),
)
