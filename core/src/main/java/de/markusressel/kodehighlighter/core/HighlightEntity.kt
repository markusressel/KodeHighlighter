package de.markusressel.kodehighlighter.core

/**
 * Data class that describes what styles would need to be applied for a single rule
 */
data class HighlightEntity(
        val start: Int,
        val end: Int,
        val styles: Set<StyleFactory>
)