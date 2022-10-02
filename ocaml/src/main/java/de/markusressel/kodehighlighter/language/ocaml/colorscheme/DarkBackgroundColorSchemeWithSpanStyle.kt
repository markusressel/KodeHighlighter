package de.markusressel.kodehighlighter.language.ocaml.colorscheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

class DarkBackgroundColorSchemeWithSpanStyle : ColorScheme<SpanStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<SpanStyle>> {
        return when (type) {
            is CommentRule -> {
                setOf { SpanStyle(Color(0xFF86858B)) }
            }
            is BooleanRule,
            is KeywordRule -> {
                setOf { SpanStyle(Color(0xFFAF38EE)) }
            }
            is ModuleRule -> {
                setOf { SpanStyle(Color(0xFF4B40E0)) }
            }
            is NumberRule -> {
                setOf { SpanStyle(Color(0xFF106BFF)) }
            }
            is StringRule -> {
                setOf { SpanStyle(Color(0xFFFD8D0E)) }
            }
            else -> emptySet()
        }
    }

}