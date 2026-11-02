package de.markusressel.kodehighlighter.core.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TokenThemeTest {

    private class TestRule(override val tokenType: TokenType?) : LanguageRule {
        override fun findMatches(text: CharSequence): List<RuleMatch> = emptyList()
    }

    @Test
    fun `github themes define all token types`() {
        listOf(GitHubDarkTheme, GitHubLightTheme).forEach { theme ->
            assertEquals(TokenType.entries.toSet(), theme.styles.keys)
        }
    }

    @Test
    fun `span style reflects the token style`() {
        val theme = TokenTheme(
            background = 0xFF000000,
            foreground = 0xFFFFFFFF,
            styles = mapOf(
                TokenType.HEADING to TokenStyle(0xFF102030, bold = true, strikethrough = true, underline = true),
            ),
        )
        val style = theme.spanStyleColorScheme.getStyles(TestRule(TokenType.HEADING)).single().invoke()

        assertEquals(Color(0xFF102030), style.color)
        assertEquals(FontWeight.Bold, style.fontWeight)
        assertEquals(TextDecoration.combine(listOf(TextDecoration.LineThrough, TextDecoration.Underline)), style.textDecoration)
    }

    @Test
    fun `rules without a token type or a style are not styled`() {
        val theme = TokenTheme(0, 0, mapOf(TokenType.KEYWORD to TokenStyle(0xFF102030)))

        assertTrue(theme.spanStyleColorScheme.getStyles(TestRule(null)).isEmpty())
        assertTrue(theme.spanStyleColorScheme.getStyles(TestRule(TokenType.STRING)).isEmpty())
    }
}
