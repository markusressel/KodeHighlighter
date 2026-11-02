package de.markusressel.kodehighlighter.core.rule

/**
 * The meaning of a matched text passage, independent of the language.
 * This allows a single theme to be used for all languages.
 */
enum class TokenType {
    KEYWORD,
    TYPE,
    FUNCTION,
    STRING,
    NUMBER,
    CONSTANT,
    COMMENT,
    ANNOTATION,
    PROPERTY,
    TAG,
    ATTRIBUTE,
    HEADING,
    STRONG,
    EMPHASIS,
    STRIKETHROUGH,
    CODE,
    LINK,
    QUOTE,
    MARKER,
    ADDITION,
    DELETION,
    META,
}
