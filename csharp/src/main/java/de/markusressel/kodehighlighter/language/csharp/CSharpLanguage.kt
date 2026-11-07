package de.markusressel.kodehighlighter.language.csharp

import de.markusressel.kodehighlighter.core.language.Language

/**
 * C#, the language of [CSharpRuleBook]
 */
object CSharpLanguage : Language(
    id = "csharp",
    displayName = "C#",
    fileExtensions = setOf("cs", "csx"),
    mimeTypes = setOf("text/x-csharp"),
    aliases = setOf("cs", "c#"),
    interpreters = setOf("dotnet"),
)
