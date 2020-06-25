package de.markusressel.kodehighlighter.core.rule

/**
 * Helper object to keep reference to the properties of a
 * matching segment found by a [SyntaxHighlighterRule]
 */
data class RuleMatch(val startIndex: Int,
                     val endIndex: Int)