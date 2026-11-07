package de.markusressel.kodehighlighter.language.javascript

import de.markusressel.kodehighlighter.core.language.Language

/**
 * JavaScript, the language of [JavaScriptRuleBook]
 */
object JavaScriptLanguage : Language(
    id = "javascript",
    displayName = "JavaScript",
    fileExtensions = setOf("js", "mjs", "cjs", "jsx", "es6", "jsm"),
    fileNames = setOf("Jakefile"),
    mimeTypes = setOf("text/javascript", "application/javascript", "application/x-javascript", "application/ecmascript", "text/ecmascript"),
    aliases = setOf("js", "jsx", "node", "ecmascript"),
    interpreters = setOf("node", "nodejs", "deno", "bun", "qjs"),
)
