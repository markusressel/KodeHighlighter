package de.markusressel.kodehighlighter.core.language

/**
 * Finds the language of a file from its name, its MIME type and its content.
 *
 * Evidence counts in this order: a modeline in the text (`vim: ft=python`, `-*- mode: python -*-`), the whole file
 * name, its extension, the MIME type, the interpreter of a shebang line, and the start of the first line. Languages
 * with more and stronger evidence come first. An extension that several languages have, like `h` for C and C++, gives
 * all of them in the order of [languages].
 *
 * @param languages the languages to choose from
 */
class LanguageDetector(val languages: List<Language>) {

    /**
     * @param fileName the name of the file, a path is fine
     * @param text the content of the file, only its first and last lines are read
     * @param mimeType the MIME type of the file, parameters like `; charset=utf-8` are ignored
     * @return the languages that fit, the most likely first, empty if none fits
     */
    fun detect(fileName: String? = null, text: CharSequence? = null, mimeType: String? = null): List<Language> {
        val scores = IntArray(languages.size)
        if (fileName != null) scoreFileName(fileName, scores)
        if (mimeType != null) scoreMimeType(mimeType, scores)
        if (text != null) scoreText(text, scores)
        return languages.indices
            .filter { scores[it] > 0 }
            .sortedByDescending { scores[it] }
            .map { languages[it] }
    }

    /** @return the most likely language, null if none fits */
    fun detectOne(fileName: String? = null, text: CharSequence? = null, mimeType: String? = null): Language? =
        detect(fileName, text, mimeType).firstOrNull()

    /** @return the language with this [id] or alias, ignoring case, null if there is none */
    fun forId(id: String): Language? {
        val name = id.lowercase()
        return languages.firstOrNull { it.id.lowercase() == name } ?: languages.firstOrNull { name in it.aliases }
    }

    private fun scoreFileName(path: String, scores: IntArray) {
        val name = path.substringAfterLast('/').substringAfterLast('\\')
        if (name.isEmpty()) return
        var matchedName = false
        languages.forEachIndexed { index, language ->
            if (name in language.fileNames) {
                scores[index] += FILE_NAME
                matchedName = true
            }
        }
        if (matchedName) return

        // the longest extension wins, so a language that claims `d.ts` beats one that claims `ts`
        val lower = name.lowercase()
        var dot = lower.indexOf('.', startIndex = 1)
        while (dot in 1 until lower.length - 1) {
            val extension = lower.substring(dot + 1)
            var matched = false
            languages.forEachIndexed { index, language ->
                if (extension in language.fileExtensions) {
                    scores[index] += EXTENSION
                    matched = true
                }
            }
            if (matched) return
            dot = lower.indexOf('.', dot + 1)
        }
    }

    private fun scoreMimeType(mimeType: String, scores: IntArray) {
        val type = mimeType.substringBefore(';').trim().lowercase()
        languages.forEachIndexed { index, language ->
            if (type in language.mimeTypes) scores[index] += MIME_TYPE
        }
    }

    private fun scoreText(text: CharSequence, scores: IntArray) {
        val head = text.subSequence(0, minOf(text.length, HEAD_LENGTH)).toString().removePrefix(BYTE_ORDER_MARK)
        val tail = if (text.length > HEAD_LENGTH) text.subSequence(text.length - TAIL_LENGTH, text.length).toString() else ""

        val modeline = modeline(head) ?: modeline(tail)
        if (modeline != null) forId(modeline)?.let { scores[languages.indexOf(it)] += MODELINE }

        val firstLine = head.substringBefore('\n').trimEnd('\r')
        if (firstLine.startsWith("#!")) {
            interpreter(firstLine)?.let { program ->
                languages.forEachIndexed { index, language ->
                    if (program in language.interpreters) scores[index] += SHEBANG
                }
            }
        }
        val start = head.trimStart()
        languages.forEachIndexed { index, language ->
            if (language.firstLinePrefixes.any { start.startsWith(it) }) scores[index] += FIRST_LINE
        }
    }

    /** @return the program of a shebang line, the one after `env` if that runs it, without a version */
    private fun interpreter(line: String): String? {
        val words = line.removePrefix("#!").trim().split(' ', '\t').filter { it.isNotEmpty() }
        var program = words.firstOrNull()?.substringAfterLast('/') ?: return null
        if (program == "env") program = words.drop(1).firstOrNull { !it.startsWith("-") && '=' !in it } ?: return null
        return program.trimEnd { it.isDigit() || it == '.' }.ifEmpty { program }
    }

    /** @return the language that an Emacs or Vim modeline in the [text] names, null if there is none */
    private fun modeline(text: String): String? {
        EMACS.find(text)?.let { match ->
            val inner = match.groupValues[1]
            val mode = EMACS_MODE.find(inner)?.groupValues?.get(1)
            return (mode ?: inner.trim().takeIf { ':' !in it && ' ' !in it })?.lowercase()?.removeSuffix("-mode")
        }
        return VIM.find(text)?.groupValues?.get(1)?.lowercase()
    }

    private companion object {
        const val MODELINE = 16
        const val FILE_NAME = 8
        const val EXTENSION = 6
        const val MIME_TYPE = 4
        const val SHEBANG = 3
        const val FIRST_LINE = 1

        const val HEAD_LENGTH = 1024
        const val TAIL_LENGTH = 512
        const val BYTE_ORDER_MARK = "﻿"

        val EMACS = Regex("""-\*-([^\n]*?)-\*-""")
        val EMACS_MODE = Regex("""(?:^|;)\s*mode:\s*([\w+#-]+)""")
        val VIM = Regex("""(?:^|\s)(?:vim?|ex):[^\n]*?\b(?:ft|filetype|syntax)=([\w+#-]+)""")
    }
}
