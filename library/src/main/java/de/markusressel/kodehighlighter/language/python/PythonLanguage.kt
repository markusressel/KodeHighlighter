package de.markusressel.kodehighlighter.language.python

import de.markusressel.kodehighlighter.core.language.Language

/**
 * Python, the language of [PythonRuleBook]
 */
object PythonLanguage : Language(
    id = "python",
    displayName = "Python",
    fileExtensions = setOf("py", "pyw", "pyi"),
    fileNames = setOf("SConstruct", "SConscript"),
    mimeTypes = setOf("text/x-python", "text/x-script.python", "application/x-python-code"),
    aliases = setOf("py", "python3"),
    interpreters = setOf("python", "pypy", "uv"),
)
