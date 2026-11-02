package de.markusressel.kodehighlighter.language.rust

import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.rust.rule.AttributeRule
import de.markusressel.kodehighlighter.language.rust.rule.CommentRule
import de.markusressel.kodehighlighter.language.rust.rule.KeywordRule
import de.markusressel.kodehighlighter.language.rust.rule.LifetimeRule
import de.markusressel.kodehighlighter.language.rust.rule.LiteralRule
import de.markusressel.kodehighlighter.language.rust.rule.MacroRule
import de.markusressel.kodehighlighter.language.rust.rule.NumberRule
import de.markusressel.kodehighlighter.language.rust.rule.StringRule
import de.markusressel.kodehighlighter.language.rust.rule.TypeRule

/**
 * Rust language rule book
 */
open class RustRuleBook : RegionRuleBook() {

    override val scanner = RustRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        AttributeRule,
        KeywordRule,
        TypeRule,
        LiteralRule,
        NumberRule,
        LifetimeRule,
        MacroRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
