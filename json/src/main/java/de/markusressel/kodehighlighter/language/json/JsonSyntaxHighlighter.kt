package de.markusressel.kodehighlighter.language.json

import de.markusressel.kodehighlighter.core.SyntaxColorScheme
import de.markusressel.kodehighlighter.core.SyntaxHighlighter
import de.markusressel.kodehighlighter.core.SyntaxHighlighterRule
import de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.json.rule.LanguageRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule

/**
 * JSON syntax highlighter
 */
class JsonSyntaxHighlighter : SyntaxHighlighter {

    override var colorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(
                StringRule(),
                NumericRule(),
                LanguageRule()
        )
    }

}