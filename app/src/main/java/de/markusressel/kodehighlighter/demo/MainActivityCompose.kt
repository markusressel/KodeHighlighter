package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.markusressel.kodehighlighter.core.ui.KodeText
import de.markusressel.kodehighlighter.core.ui.KodeTextField
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorSchemeWithSpanStyle


class MainActivityCompose : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            ViewTypeSelection(uiState)

            ComposeSamples(
                selectedViewType = uiState.selectedViewType,
                selectedLanguage = uiState.selectedLanguage
            )
        }
    }

    @Composable
    private fun ViewTypeSelection(uiState: UiState) {
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.onUiEvent(UiEvent.ViewTypeSelected(ViewType.Text))
                }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Text"
            )
            Checkbox(
                checked = uiState.selectedViewType == ViewType.Text,
                onCheckedChange = { viewModel.onUiEvent(UiEvent.ViewTypeSelected(ViewType.Text)) }
            )
        }

        Row(
            modifier = Modifier
                .clickable {
                    viewModel.onUiEvent(UiEvent.ViewTypeSelected(ViewType.Editor))
                }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Editor"
            )
            Checkbox(
                checked = uiState.selectedViewType == ViewType.Editor,
                onCheckedChange = { viewModel.onUiEvent(UiEvent.ViewTypeSelected(ViewType.Editor)) }
            )
        }
    }

    @Composable
    private fun ComposeSamples(
        selectedViewType: ViewType,
        selectedLanguage: LanguageExample
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            when (selectedViewType) {
                ViewType.Text -> KodeTextExample()
                ViewType.Editor -> KodeTextFieldExample()
            }
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