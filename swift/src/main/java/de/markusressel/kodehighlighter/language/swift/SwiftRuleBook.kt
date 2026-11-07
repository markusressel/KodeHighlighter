package de.markusressel.kodehighlighter.language.swift

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.swift.rule.AttributeRule
import de.markusressel.kodehighlighter.language.swift.rule.CommentRule
import de.markusressel.kodehighlighter.language.swift.rule.DirectiveRule
import de.markusressel.kodehighlighter.language.swift.rule.KeywordRule
import de.markusressel.kodehighlighter.language.swift.rule.LiteralRule
import de.markusressel.kodehighlighter.language.swift.rule.NumberRule
import de.markusressel.kodehighlighter.language.swift.rule.StringRule
import de.markusressel.kodehighlighter.language.swift.rule.TypeRule

/**
 * Swift language rule book
 */
open class SwiftRuleBook : RegionRuleBook() {

    override val language: Language get() = SwiftLanguage

    override val scanner = SwiftRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        AttributeRule,
        DirectiveRule,
        KeywordRule,
        TypeRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
