package de.markusressel.kodehighlighter.core.ui

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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
    value: TextFieldValue,
    languageRuleBook: LanguageRuleBook,
    colorScheme: ColorScheme<SpanStyle>,
    onValueChange: (TextFieldValue) -> Unit,
) {
    val visualTransformation = HighlightingTransformation(languageRuleBook, colorScheme)

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
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
        modifier,
        value,
        languageRuleBook,
        colorScheme,
        onValueChange,
    )
}

