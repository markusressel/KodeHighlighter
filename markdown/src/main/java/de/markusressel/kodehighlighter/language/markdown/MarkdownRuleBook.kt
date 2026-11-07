package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.markdown.rule.AutolinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.BlockquoteRule
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeLineRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.StrikeRule
import de.markusressel.kodehighlighter.language.markdown.rule.TaskRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule

/**
 * Markdown language rule book
 */
open class MarkdownRuleBook : RegionRuleBook() {

    override val language: Language get() = MarkdownLanguage

    override val scanner = MarkdownRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        HeadingRule,
        BlockquoteRule,
        ItalicRule,
        BoldRule,
        CodeLineRule,
        AutolinkRule,
        TextLinkRule,
        ImageLinkRule,
        StrikeRule,
        ListRule,
        TaskRule,
        HorizontalRuleRule,
        CodeInlineRule,
        CodeBlockRule
    )

    override fun getRules() = rules

}
