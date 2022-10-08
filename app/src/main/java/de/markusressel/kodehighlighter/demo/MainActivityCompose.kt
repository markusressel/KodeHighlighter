package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.markusressel.kodehighlighter.core.ui.KodeText
import de.markusressel.kodehighlighter.core.ui.KodeTextField
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorSchemeWithSpanStyle


class MainActivityCompose : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ComposeSamples()
            }
        }
    }

    @Composable
    private fun ComposeSamples() {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            KodeTextExample()
            KodeTextFieldExample()
            Text(text = "Legaxy XML Views")
        }
    }

    @Composable
    private fun KodeTextExample() {
        val text by rememberSaveable {
            mutableStateOf(resources.readResourceFileAsText(R.raw.markdown_sample))
        }
        val ruleBook by remember { mutableStateOf(MarkdownRuleBook()) }
        val colorScheme by remember {
            mutableStateOf(
                DarkBackgroundColorSchemeWithSpanStyle()
            )
        }

        Text(text = "Compose KodeText")

        val borderSize = 2.dp

        KodeText(
            modifier = Modifier
                .border(BorderStroke(borderSize, color = MaterialTheme.colors.primary))
                .padding(borderSize * 2)
                .wrapContentSize()
                .fillMaxWidth(),
            text = text,
            languageRuleBook = ruleBook,
            colorScheme = colorScheme,
        )
    }

    @Composable
    private fun KodeTextFieldExample() {
        var text by rememberSaveable {
            mutableStateOf(resources.readResourceFileAsText(R.raw.markdown_sample))
        }
        val ruleBook by remember { mutableStateOf(MarkdownRuleBook()) }
        val colorScheme by remember {
            mutableStateOf(
                DarkBackgroundColorSchemeWithSpanStyle()
            )
        }

        var textFieldValue by remember {
            mutableStateOf(
                TextFieldValue(
                    annotatedString = AnnotatedString(text),
                )
            )
        }

        Text(text = "Compose KodeTextField")

        val borderSize = 2.dp

        KodeTextField(
            modifier = Modifier
                .border(BorderStroke(borderSize, color = MaterialTheme.colors.primary))
                .padding(borderSize * 2)
                .wrapContentSize()
                .fillMaxWidth(),
            value = textFieldValue,
            languageRuleBook = ruleBook,
            colorScheme = colorScheme,
            onValueChange = {
                if (it.text != text) {
                    text = it.text
                }
                if (it != textFieldValue) {
                    textFieldValue = it
                }
            }
        )
    }

}