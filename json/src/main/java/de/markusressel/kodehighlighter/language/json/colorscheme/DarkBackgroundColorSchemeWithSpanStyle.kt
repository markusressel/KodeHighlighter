package de.markusressel.kodehighlighter.language.json.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.language.json.rule.KeywordRule
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule
import java.util.Collections.emptySet

/**
 * A dark color scheme for json to be used for compose
 */
class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: de.markusressel.kodehighlighter.core.rule.LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is StringRule -> {
                setOf { SpanStyle(Color(0xFF6A8759)) }
            }
            is NumericRule -> {
                setOf { SpanStyle(Color(0xFF7C4DFF)) }
            }
            is KeywordRule -> {
                setOf { SpanStyle(Color(0xFFFF6D00)) }
            }
            else -> emptySet()
        }
    }

}