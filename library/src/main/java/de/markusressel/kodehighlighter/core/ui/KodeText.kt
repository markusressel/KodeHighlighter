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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A text with support for code highlighting. Highlighting runs off the main thread,
 * the previous result stays visible until the next one is available.
 *
 * @param modifier the modifier to apply to this component
 * @param text the text value of this component
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 * @param textColor the color of text that is not highlighted
 * @param fontSize the size of the text
 * @param fontFamily the font family of the text
 * @param letterSpacing the spacing between letters
 * @param textDecoration the decoration of the text
 * @param textAlign the alignment of the text
 * @param lineHeight the height of a line
 * @param overflow how to handle text that does not fit
 * @param softWrap whether the text can break at soft line breaks
 * @param maxLines the maximum number of visible lines
 * @param minLines the minimum number of visible lines
 * @param onTextLayout callback for a new text layout
 *
 * @see [KodeTextField]
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

    var latestResult by remember(composeHighlighter) {
        mutableStateOf<AnnotatedString?>(null)
    }

    LaunchedEffect(text, composeHighlighter) {
        latestResult = withContext(Dispatchers.Default) {
            composeHighlighter.highlight(text)
        }
    }
    val annotatedText = remember(text, latestResult) {
        applyLatestHighlighting(text, latestResult)
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
