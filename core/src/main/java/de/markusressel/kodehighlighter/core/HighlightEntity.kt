package de.markusressel.kodehighlighter.core

import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule

/**
 * Data class that describes what styles would need to be applied for a single rule
 */
data class HighlightEntity(
        val rule: SyntaxHighlighterRule,
        val matches: List<RuleMatch>
)