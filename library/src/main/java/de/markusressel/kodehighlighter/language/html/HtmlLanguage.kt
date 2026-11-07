package de.markusressel.kodehighlighter.language.html

import de.markusressel.kodehighlighter.core.language.Language

/**
 * HTML, the language of [HtmlRuleBook]
 */
object HtmlLanguage : Language(
    id = "html",
    displayName = "HTML",
    fileExtensions = setOf("html", "htm", "shtml", "xht", "mdoc", "jsp", "asp", "aspx", "jshtm"),
    mimeTypes = setOf("text/html"),
    aliases = setOf("htm"),
    firstLinePrefixes = listOf("<!DOCTYPE html", "<!doctype html", "<!DOCTYPE HTML", "<html"),
)
