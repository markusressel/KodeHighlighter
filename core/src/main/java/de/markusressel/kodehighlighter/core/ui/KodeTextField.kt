package de.markusressel.kodehighlighter.core.ui

import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.TextFieldValue
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.util.AnnotatedStringHighlighter
import kotlinx.coroutines.delay

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
    val composeHighlighter by remember {
        mutableStateOf(
            AnnotatedStringHighlighter(
                languageRuleBook = languageRuleBook,
                colorScheme = colorScheme,
            )
        )
    }

    var annotatedText by remember(value.text) {
        mutableStateOf(AnnotatedString(text = value.text))
    }

    TextField(
        modifier = modifier,
        value = TextFieldValue(
            annotatedString = annotatedText,
            selection = value.selection,
        ),
        onValueChange = {
            onValueChange(it)
        },
    )

    LaunchedEffect(value.text) {
        delay(300)
        annotatedText = composeHighlighter.highlight(value.text)
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
    KodeEditText(
        modifier,
        value,
        languageRuleBook,
        colorScheme,
        onValueChange,
    )
}

