package de.markusressel.kodehighlighter.core.colorscheme

import android.graphics.Typeface
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.TokenType

/**
 * The look of a single [TokenType]
 *
 * @param color the text color as ARGB, for example `0xFFFF7B72`
 * @param bold whether the text is bold
 * @param italic whether the text is italic
 * @param strikethrough whether the text is struck through
 * @param underline whether the text is underlined
 */
data class TokenStyle(
    val color: Long,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val strikethrough: Boolean = false,
    val underline: Boolean = false,
)

/**
 * A theme that works for every language whose rules define a [LanguageRule.tokenType].
 * Rules without one are not styled.
 *
 * @param background the background color the theme is meant for as ARGB, it is not applied automatically
 * @param foreground the color of text that is not highlighted as ARGB, it is not applied automatically
 * @param styles the look of each token type
 */
open class TokenTheme(
    val background: Long,
    val foreground: Long,
    val styles: Map<TokenType, TokenStyle>,
) {

    /**
     * This theme as a color scheme for Compose
     */
    val spanStyleColorScheme: ColorScheme<SpanStyle> by lazy {
        TokenColorScheme(styles.mapValues { (_, style) -> setOf(style.spanStyleFactory()) })
    }

    /**
     * This theme as a color scheme for Spannables
     */
    val characterStyleColorScheme: ColorScheme<CharacterStyle> by lazy {
        TokenColorScheme(styles.mapValues { (_, style) -> style.characterStyleFactories() })
    }
}

private class TokenColorScheme<Style>(
    private val factories: Map<TokenType, Set<StyleFactory<Style>>>,
) : ColorScheme<Style> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<Style>> {
        return type.tokenType?.let(factories::get) ?: emptySet()
    }
}

private fun TokenStyle.spanStyleFactory(): StyleFactory<SpanStyle> {
    val decorations = listOfNotNull(
        TextDecoration.LineThrough.takeIf { strikethrough },
        TextDecoration.Underline.takeIf { underline },
    )
    val spanStyle = SpanStyle(
        color = Color(color),
        fontWeight = if (bold) FontWeight.Bold else null,
        fontStyle = if (italic) FontStyle.Italic else null,
        textDecoration = if (decorations.isEmpty()) null else TextDecoration.combine(decorations),
    )
    return { spanStyle }
}

private fun TokenStyle.characterStyleFactories(): Set<StyleFactory<CharacterStyle>> {
    val colorInt = color.toInt()
    val typeface = when {
        bold && italic -> Typeface.BOLD_ITALIC
        bold -> Typeface.BOLD
        italic -> Typeface.ITALIC
        else -> null
    }
    // a span can only be attached to one position, so every factory creates a new one
    return buildSet<StyleFactory<CharacterStyle>> {
        add { ForegroundColorSpan(colorInt) }
        if (typeface != null) add { StyleSpan(typeface) }
        if (strikethrough) add { StrikethroughSpan() }
        if (underline) add { UnderlineSpan() }
    }
}
