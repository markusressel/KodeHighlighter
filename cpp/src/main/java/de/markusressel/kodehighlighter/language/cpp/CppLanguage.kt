package de.markusressel.kodehighlighter.language.cpp

import de.markusressel.kodehighlighter.core.language.Language

/**
 * C++, the language of [CppRuleBook]
 */
object CppLanguage : Language(
    id = "cpp",
    displayName = "C++",
    fileExtensions = setOf("cpp", "cc", "cxx", "c++", "hpp", "hh", "hxx", "h++", "h", "ipp", "tpp", "inl", "ino"),
    mimeTypes = setOf("text/x-c++", "text/x-c++src", "text/x-c++hdr"),
    aliases = setOf("c++", "cxx"),
)
