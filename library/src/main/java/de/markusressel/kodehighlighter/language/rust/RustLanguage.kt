package de.markusressel.kodehighlighter.language.rust

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Rust, the language of [RustRuleBook]
 */
object RustLanguage : Language(
    id = "rust",
    displayName = "Rust",
    fileExtensions = setOf("rs"),
    mimeTypes = setOf("text/rust", "text/x-rust"),
    aliases = setOf("rs"),
)
