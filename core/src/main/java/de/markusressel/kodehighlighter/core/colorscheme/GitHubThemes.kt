package de.markusressel.kodehighlighter.core.colorscheme

import de.markusressel.kodehighlighter.core.rule.TokenType

/**
 * GitHub's dark colors, as used by the highlight.js `github-dark` style
 */
object GitHubDarkTheme : TokenTheme(
    background = 0xFF0D1117,
    foreground = 0xFFC9D1D9,
    styles = mapOf(
        TokenType.KEYWORD to TokenStyle(0xFFFF7B72),
        TokenType.TYPE to TokenStyle(0xFFFFA657),
        TokenType.FUNCTION to TokenStyle(0xFFD2A8FF),
        TokenType.STRING to TokenStyle(0xFFA5D6FF),
        TokenType.NUMBER to TokenStyle(0xFF79C0FF),
        TokenType.CONSTANT to TokenStyle(0xFF79C0FF),
        TokenType.COMMENT to TokenStyle(0xFF8B949E),
        TokenType.ANNOTATION to TokenStyle(0xFF79C0FF),
        TokenType.PROPERTY to TokenStyle(0xFF79C0FF),
        TokenType.TAG to TokenStyle(0xFF7EE787),
        TokenType.ATTRIBUTE to TokenStyle(0xFF79C0FF),
        TokenType.HEADING to TokenStyle(0xFF1F6FEB, bold = true),
        TokenType.STRONG to TokenStyle(0xFFC9D1D9, bold = true),
        TokenType.EMPHASIS to TokenStyle(0xFFC9D1D9, italic = true),
        TokenType.STRIKETHROUGH to TokenStyle(0xFFC9D1D9, strikethrough = true),
        TokenType.CODE to TokenStyle(0xFF8B949E),
        TokenType.LINK to TokenStyle(0xFFA5D6FF, underline = true),
        TokenType.QUOTE to TokenStyle(0xFF7EE787),
        TokenType.MARKER to TokenStyle(0xFFF2CC60),
        TokenType.ADDITION to TokenStyle(0xFFAFF5B4),
        TokenType.DELETION to TokenStyle(0xFFFFDCD7),
        TokenType.META to TokenStyle(0xFF79C0FF),
    ),
)

/**
 * GitHub's light colors, as used by the highlight.js `github` style
 */
object GitHubLightTheme : TokenTheme(
    background = 0xFFFFFFFF,
    foreground = 0xFF24292E,
    styles = mapOf(
        TokenType.KEYWORD to TokenStyle(0xFFD73A49),
        TokenType.TYPE to TokenStyle(0xFFE36209),
        TokenType.FUNCTION to TokenStyle(0xFF6F42C1),
        TokenType.STRING to TokenStyle(0xFF032F62),
        TokenType.NUMBER to TokenStyle(0xFF005CC5),
        TokenType.CONSTANT to TokenStyle(0xFF005CC5),
        TokenType.COMMENT to TokenStyle(0xFF6A737D),
        TokenType.ANNOTATION to TokenStyle(0xFF005CC5),
        TokenType.PROPERTY to TokenStyle(0xFF005CC5),
        TokenType.TAG to TokenStyle(0xFF22863A),
        TokenType.ATTRIBUTE to TokenStyle(0xFF005CC5),
        TokenType.HEADING to TokenStyle(0xFF005CC5, bold = true),
        TokenType.STRONG to TokenStyle(0xFF24292E, bold = true),
        TokenType.EMPHASIS to TokenStyle(0xFF24292E, italic = true),
        TokenType.STRIKETHROUGH to TokenStyle(0xFF24292E, strikethrough = true),
        TokenType.CODE to TokenStyle(0xFF6A737D),
        TokenType.LINK to TokenStyle(0xFF032F62, underline = true),
        TokenType.QUOTE to TokenStyle(0xFF22863A),
        TokenType.MARKER to TokenStyle(0xFF735C0F),
        TokenType.ADDITION to TokenStyle(0xFF22863A),
        TokenType.DELETION to TokenStyle(0xFFB31D28),
        TokenType.META to TokenStyle(0xFF005CC5),
    ),
)
