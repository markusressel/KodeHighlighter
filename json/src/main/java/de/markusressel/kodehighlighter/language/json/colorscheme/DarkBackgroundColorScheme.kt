package de.markusressel.kodehighlighter.language.json.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.language.json.rule.NumericRule
import de.markusressel.kodehighlighter.language.json.rule.StringRule
import java.util.Collections.emptySet

/**
 * A dark color scheme for json
 */
class DarkBackgroundColorScheme : ColorScheme {

    override fun getStyles(type: de.markusressel.kodehighlighter.core.rule.LanguageRule): Set<StyleFactory> {
        return when (type) {
            is StringRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#6A8759")) }
            }
            is NumericRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#7C4DFF")) }
            }
            is LanguageRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            else -> emptySet()
        }
    }

}