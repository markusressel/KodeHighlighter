package de.markusressel.kodehighlighter.core.ui

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.util.AnnotatedStringHighlighter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


/**
 * An [BasicTextField] based Text Editor composable.
 *
 * @param modifier the modifier to apply to this component
 * @param enabled whether the text field is enabled
 * @param readOnly whether the contents of the text field can be edited by the user
 * @param value the [TextFieldValue] value of the text field
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 * @param onValueChange callback for changes of the current value
 * @param colors a set of colors for different parts of the view
 * @param textStyle the styling of the text ([AnnotatedString] can override this)
 * @param onTextLayout callback for a new text layout
 * @param decorationBox a composable to decorate the inner text field
 *
 * @see [KodeText]
 */
@Composable
fun KodeTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = enabled.not(),
    value: TextFieldValue,
    languageRuleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>,
    onValueChange: (TextFieldValue) -> Unit,
    colors: KodeTextFieldColors = KodeTextFieldDefaults.textFieldColors(),
    textStyle: TextStyle = LocalTextStyle.current,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    val highlighter = remember(languageRuleBook, colorScheme) {
        AnnotatedStringHighlighter(languageRuleBook, colorScheme)
    }
    var highlightedText by remember(highlighter) {
        mutableStateOf<AnnotatedString?>(null)
    }
    LaunchedEffect(value.text, highlighter) {
        highlightedText = withContext(Dispatchers.Default) {
            highlighter.highlight(value.text)
        }
    }
    val visualTransformation = remember(highlightedText) {
        LatestHighlightingTransformation(highlightedText)
    }

    BasicTextField(
        modifier = modifier,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(colors.cursorColor().value),
        textStyle = mergedTextStyle,
        readOnly = readOnly,
        onTextLayout = onTextLayout,
        decorationBox = decorationBox,
    )
}

/**
 * Highlights on the calling thread, which blocks the UI on large inputs.
 * [KodeTextField] no longer uses this.
 */
class HighlightingTransformation(
    ruleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>
) : VisualTransformation {

    private val highlighter = AnnotatedStringHighlighter(ruleBook, colorScheme)

    override fun filter(text: AnnotatedString): TransformedText {
        val highlightedText = runBlocking {
            highlighter.highlight(text.text)
        }
        return TransformedText(highlightedText, OffsetMapping.Identity)
    }
}

/**
 * Shows the most recent highlighting result, which is computed in the background.
 *
 * @param highlightedText the latest result, null if there is none yet
 */
private class LatestHighlightingTransformation(
    private val highlightedText: AnnotatedString?
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            applyLatestHighlighting(text.text, highlightedText),
            OffsetMapping.Identity
        )
    }
}

/**
 * Applies a highlighting result to a text, which may have changed since the result was computed.
 * Until the next result arrives, the previous styles move with the edit, so that colors do not flicker.
 * A style that ends where text was inserted grows with it, which keeps typing at the end of a token stable.
 *
 * @param text the current text
 * @param highlightedText the latest result, null if there is none yet
 * @return the text with styles
 */
internal fun applyLatestHighlighting(
    text: String,
    highlightedText: AnnotatedString?
): AnnotatedString {
    if (highlightedText == null) return AnnotatedString(text)
    if (highlightedText.text == text) return highlightedText

    // the edit replaced [editStart, oldEditEnd) of the old text with [editStart, editEnd) of the new one
    val oldText = highlightedText.text
    val editStart = oldText.commonPrefixWith(text).length
    val maxSuffix = minOf(oldText.length, text.length) - editStart
    var suffix = 0
    while (suffix < maxSuffix && oldText[oldText.length - 1 - suffix] == text[text.length - 1 - suffix]) {
        suffix++
    }
    val oldEditEnd = oldText.length - suffix
    val delta = text.length - oldText.length

    return buildAnnotatedString {
        append(text)
        highlightedText.spanStyles.forEach { span ->
            val start = when {
                span.start < editStart -> span.start
                span.start >= oldEditEnd -> span.start + delta
                else -> editStart
            }
            val end = when {
                span.end < editStart -> span.end
                span.end >= oldEditEnd -> span.end + delta
                else -> editStart
            }
            if (start < end) {
                addStyle(span.item, start, end)
            }
        }
    }
}

@Preview
@Composable
private fun KodeTextFieldPreview() {
    KodeTextField(
        value = TextFieldValue("Hello, World!\nThis is a test!"),
        languageRuleBook = object : LanguageRuleBook {
            override fun getRules(): List<LanguageRule> {
                return emptyList()
            }
        },
        colorScheme = object : ColorScheme<SpanStyle> {
            override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
                return emptySet()
            }
        },
        onValueChange = {},
    )
}