package de.markusressel.kodehighlighter.core.ui

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.util.AnnotatedStringHighlighter


/**
 * An text with support for code highlighting.
 *
 * @param text the text value of this component
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 *
 * @see [KodeText]
 */
@Composable
fun KodeText(
    modifier: Modifier = Modifier,
    text: String,
    languageRuleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>,
    textColor: Color = LocalTextStyle.current.color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    val composeHighlighter by remember(languageRuleBook, colorScheme) {
        mutableStateOf(
            AnnotatedStringHighlighter(
                languageRuleBook = languageRuleBook,
                colorScheme = colorScheme,
            )
        )
    }

    var annotatedText by remember(text) {
        mutableStateOf(AnnotatedString(text = text))
    }

    LaunchedEffect(text, composeHighlighter) {
        annotatedText = composeHighlighter.highlight(annotatedText)
    }

    Text(
        modifier = modifier,
        text = annotatedText,
        color = textColor,
        fontSize = fontSize,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
    )
}
