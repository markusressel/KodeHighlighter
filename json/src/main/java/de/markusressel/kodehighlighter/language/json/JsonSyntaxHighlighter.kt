package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.colorscheme.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.rule.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.json.rule.LanguageRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * JSON syntax highlighter
 */
class JsonSyntaxHighlighter : SyntaxHighlighter {

    override val defaultColorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(
                StringRule(),
                NumericRule(),
                LanguageRule()
        )
    }

}