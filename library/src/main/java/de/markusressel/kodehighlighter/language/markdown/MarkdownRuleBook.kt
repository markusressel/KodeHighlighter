package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.languages.KodeHighlighterLanguages
import java.util.concurrent.ConcurrentHashMap
import de.markusressel.kodehighlighter.language.markdown.rule.AutolinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.BlockquoteRule
import de.markusressel.kodehighlighter.language.markdown.rule.BoldRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeLineRule
import de.markusressel.kodehighlighter.language.markdown.rule.FrontMatterRule
import de.markusressel.kodehighlighter.language.markdown.rule.HeadingRule
import de.markusressel.kodehighlighter.language.markdown.rule.HorizontalRuleRule
import de.markusressel.kodehighlighter.language.markdown.rule.ImageLinkRule
import de.markusressel.kodehighlighter.language.markdown.rule.ItalicRule
import de.markusressel.kodehighlighter.language.markdown.rule.ListRule
import de.markusressel.kodehighlighter.language.markdown.rule.StrikeRule
import de.markusressel.kodehighlighter.language.markdown.rule.TaskRule
import de.markusressel.kodehighlighter.language.markdown.rule.TextLinkRule
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook

/**
 * Markdown language rule book. YAML front matter at the start of the text is highlighted with the rules of
 * [YamlRuleBook].
 *
 * @param codeBlockRuleBook the rule book for the content of a fenced code block, by the language of its info string,
 * like `kotlin` for ```` ```kotlin ````. It is asked once per language. Null highlights the whole block as code, like
 * blocks without a language. By default every language of KodeHighlighter, `{ null }` highlights no code block
 */
open class MarkdownRuleBook(
    private val codeBlockRuleBook: (language: String) -> LanguageRuleBook? = KodeHighlighterLanguages::ruleBookForName,
) : RegionRuleBook() {

    override val language: Language get() = MarkdownLanguage

    // a list, because a map can't hold null
    private val codeBlockRuleBooks = ConcurrentHashMap<String, List<LanguageRuleBook>>()

    override val scanner = MarkdownRegions.scanner { language ->
        codeBlockRuleBooks.getOrPut(language) { listOfNotNull(codeBlockRuleBook(language)) }.firstOrNull()
    }

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
        CodeBlockRule,
        FrontMatterRule,
    )

    override fun getRules() = rules

}
