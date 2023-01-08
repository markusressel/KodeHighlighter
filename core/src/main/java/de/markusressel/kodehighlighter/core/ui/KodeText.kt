package de.markusressel.kodehighlighter.core.ui

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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
        text = annotatedText
    )
}
