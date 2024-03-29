package de.markusressel.kodehighlighter.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.ui.KodeText
import de.markusressel.kodehighlighter.core.ui.KodeTextField
import de.markusressel.kodehighlighter.demo.theme.KodeHighlighterTheme
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook


class MainActivityCompose : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KodeHighlighterTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
        ) {
            ViewTypeSelection(uiState)

            ExampleFileSelection(
                currentLanguage = uiState.selectedLanguage,
                availableLanguages = uiState.availableLanguages,
                onLanguageSelected = {
                    viewModel.onUiEvent(UiEvent.LanguageExampleSelected(it))
                }
            )

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
                text = "Text",
                color = MaterialTheme.colors.onSurface,
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
                text = "Editor",
                color = MaterialTheme.colors.onSurface,
            )
            Checkbox(
                checked = uiState.selectedViewType == ViewType.Editor,
                onCheckedChange = { viewModel.onUiEvent(UiEvent.ViewTypeSelected(ViewType.Editor)) }
            )
        }
    }

    @Composable
    private fun ExampleFileSelection(
        currentLanguage: LanguageExample,
        availableLanguages: List<LanguageExample>,
        onLanguageSelected: (LanguageExample) -> Unit,
    ) {
        var expanded by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .clickable {
                    expanded = expanded.not()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "Language: ${currentLanguage.name}",
                color = MaterialTheme.colors.onSurface,
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column {
                    availableLanguages.forEach { language ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onLanguageSelected(language)
                                    expanded = false
                                }
                                .padding(8.dp),
                            text = language.name,
                        )
                    }
                }
            }
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
            val text by remember(selectedLanguage) {
                mutableStateOf(resources.readResourceFileAsText(selectedLanguage.resource))
            }
            val ruleBook by remember(selectedLanguage) {
                derivedStateOf {
                    when (selectedLanguage) {
                        markdownExample -> MarkdownRuleBook()
                        jsonExample -> JsonRuleBook()
                        javaExample -> JavaRuleBook()
                        pythonExample -> PythonRuleBook()
                        else -> KotlinRuleBook()
                    }
                }
            }
            val colorScheme by remember(selectedLanguage) {
                derivedStateOf {
                    when (selectedLanguage) {
                        markdownExample -> de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorSchemeWithSpanStyle()
                        jsonExample -> de.markusressel.kodehighlighter.language.json.colorscheme.DarkBackgroundColorSchemeWithSpanStyle()
                        javaExample -> de.markusressel.kodehighlighter.language.java.colorscheme.DarkBackgroundColorSchemeWithSpanStyle()
                        pythonExample -> de.markusressel.kodehighlighter.language.python.colorscheme.DarkBackgroundColorSchemeWithSpanStyle()
                        else -> de.markusressel.kodehighlighter.language.kotlin.colorscheme.DarkBackgroundColorSchemeWithSpanStyle()
                    }
                }
            }

            when (selectedViewType) {
                ViewType.Text -> KodeTextExample(
                    text = text,
                    ruleBook = ruleBook,
                    colorScheme = colorScheme,
                )
                ViewType.Editor -> KodeTextFieldExample(
                    initialText = text,
                    ruleBook = ruleBook,
                    colorScheme = colorScheme,
                )
            }
        }
    }

    @Composable
    private fun KodeTextExample(
        text: String,
        ruleBook: LanguageRuleBook,
        colorScheme: ColorScheme<SpanStyle>
    ) {
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
            textColor = MaterialTheme.colors.onSurface,
        )
    }

    @Composable
    private fun KodeTextFieldExample(
        initialText: String,
        ruleBook: LanguageRuleBook,
        colorScheme: ColorScheme<SpanStyle>,
    ) {
        var textFieldValue by remember(initialText) {
            mutableStateOf(
                TextFieldValue(
                    annotatedString = AnnotatedString(initialText),
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
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colors.onSurface),
            onValueChange = {
                if (it != textFieldValue) {
                    textFieldValue = it
                }
            }
        )
    }

}