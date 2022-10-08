package de.markusressel.kodehighlighter.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.util.AnnotatedStringHighlighter
import kotlinx.coroutines.runBlocking


/**
 * An [InputField] based Text Editor composable.
 * The same thing as [KodeEditText] but with a name that matches compose naming style.
 *
 * @param value the [TextFieldValue] value of the text field.
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 * @param onValueChange callback for changes o the current value
 *
 * @see [KodeEditText]
 */
@Composable
fun KodeTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: TextFieldValue,
    languageRuleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>,
    onValueChange: (TextFieldValue) -> Unit,
    colors: KodeTextFieldColors = KodeTextFieldDefaults.textFieldColors(),
    textStyle: TextStyle = LocalTextStyle.current,
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    val visualTransformation = HighlightingTransformation(languageRuleBook, colorScheme)

    BasicTextField(
        modifier = Modifier
            .background(colors.backgroundColor(enabled).value)
            .then(modifier),
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(colors.cursorColor().value),
        textStyle = mergedTextStyle,
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

/**
 * An [InputField] based Text Editor composable.
 * The same thing as [KodeTextField] but with a name that matches XML widget naming style.
 *
 * @param value the [TextFieldValue] value of the text field.
 * @param languageRuleBook the language rules to use for highlighting
 * @param colorScheme the color scheme to use for highlighting
 * @param onValueChange callback for changes o the current value
 *
 * @see [KodeTextField]
 */
@Composable
fun KodeEditText(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    languageRuleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>,
    onValueChange: (TextFieldValue) -> Unit,
) {
    KodeTextField(
        modifier = modifier,
        value = value,
        languageRuleBook = languageRuleBook,
        colorScheme = colorScheme,
        onValueChange = onValueChange,
    )
}

