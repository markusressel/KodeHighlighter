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
import kotlinx.coroutines.runBlocking


/**
 * An [BasicTextField] based Text Editor composable.
 *
 * @param enabled whether the text field is enabled
 * @param readOnly whether the contents of the text field can be edited by the user
 * @param value the [TextFieldValue] value of the text field.
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 * @param onValueChange callback for changes o the current value
 * @param colors a set of colors for different parts of the view
 * @param textStyle the styling of the text ([AnnotatedString] can override this)
 *
 * @see [KodeEditText]
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

    val visualTransformation = HighlightingTransformation(languageRuleBook, colorScheme)

    BasicTextField(
        modifier = modifier,
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

class HighlightingTransformation(
    ruleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>
) : VisualTransformation {

    private val highlighter = AnnotatedStringHighlighter(ruleBook, colorScheme)

    override fun filter(text: AnnotatedString): TransformedText {
        // TODO: is it possible to run this asynchronously somehow?
        val highlightedText = runBlocking {
            highlighter.highlight(text.text)
        }
        return TransformedText(highlightedText, OffsetMapping.Identity)
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