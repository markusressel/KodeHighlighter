package de.markusressel.kodehighlighter.language.diff

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Diff, the language of [DiffRuleBook]
 */
object DiffLanguage : Language(
    id = "diff",
    displayName = "Diff",
    fileExtensions = setOf("diff", "patch"),
    mimeTypes = setOf("text/x-diff", "text/x-patch"),
    aliases = setOf("patch"),
    firstLinePrefixes = listOf("diff --git ", "diff -", "Index: "),
)
