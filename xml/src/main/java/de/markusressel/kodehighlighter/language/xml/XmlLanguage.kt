package de.markusressel.kodehighlighter.language.xml

import de.markusressel.kodehighlighter.core.language.Language

/**
 * XML, the language of [XmlRuleBook]
 */
object XmlLanguage : Language(
    id = "xml",
    displayName = "XML",
    fileExtensions = setOf("xml", "xsd", "xsl", "xslt", "svg", "xhtml", "plist", "rss", "atom", "wsdl", "xaml", "resx", "csproj", "vbproj", "fsproj", "vcxproj", "props", "targets", "nuspec", "pom", "kml", "gpx", "iml", "storyboard", "xib", "fxml", "jrxml", "tld", "dtd"),
    fileNames = setOf(".classpath", ".project"),
    mimeTypes = setOf("application/xml", "text/xml", "image/svg+xml", "application/xhtml+xml", "application/rss+xml", "application/atom+xml"),
    aliases = setOf("xhtml", "svg"),
    firstLinePrefixes = listOf("<?xml"),
)
