package de.markusressel.kodehighlighter.core.language

/**
 * A language and how to recognize its files. Each language module has one as an `object`, for example
 * `KotlinLanguage`, and its rule book returns it as [de.markusressel.kodehighlighter.core.LanguageRuleBook.language].
 * Two languages are the same if they have the same [id].
 *
 * @param id a stable name for code, lower case. The ids of the built-in languages are those of VS Code, like `kotlin`,
 * `cpp` or `csharp`
 * @param displayName the name to show to users
 * @param fileExtensions the extensions of its files without the dot, lower case. An extension can contain dots
 * @param fileNames whole names of its files that have no fitting extension, like `Dockerfile`
 * @param mimeTypes the MIME types of its files
 * @param aliases other names for the language, for example in modelines like `vim: ft=py`, lower case
 * @param interpreters the programs in a shebang line like `#!/usr/bin/env python3` that run its files, without a
 * version at the end, so `python` for `python3.12`
 * @param firstLinePrefixes what the first line of its files can start with, like `<?xml`
 */
open class Language(
    val id: String,
    val displayName: String,
    val fileExtensions: Set<String> = emptySet(),
    val fileNames: Set<String> = emptySet(),
    val mimeTypes: Set<String> = emptySet(),
    val aliases: Set<String> = emptySet(),
    val interpreters: Set<String> = emptySet(),
    val firstLinePrefixes: List<String> = emptyList(),
) {
    final override fun equals(other: Any?): Boolean = other is Language && other.id == id

    final override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Language($id)"
}
