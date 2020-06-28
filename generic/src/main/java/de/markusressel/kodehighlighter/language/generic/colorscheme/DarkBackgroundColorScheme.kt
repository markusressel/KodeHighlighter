package de.markusressel.kodehighlighter.language.generic.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.generic.rule.*

/**
 * A dark color scheme
 */
class DarkBackgroundColorScheme : ColorScheme {

    override fun getStyles(type: LanguageRule): Set<StyleFactory> {
        return when (type) {
            is KeywordRule,
            is NumericRule,
            is OperatorRule,
            is ConstantsRule,
            is EscapeCharRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#5D4037")) }
            }
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#33691E")) }
            }
            else -> emptySet()
        }
    }

}