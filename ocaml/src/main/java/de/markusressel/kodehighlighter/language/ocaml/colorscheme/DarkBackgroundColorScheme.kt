package de.markusressel.kodehighlighter.language.ocaml.colorscheme

import android.graphics.Color
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.ocaml.rule.BooleanRule
import de.markusressel.kodehighlighter.language.ocaml.rule.CommentRule
import de.markusressel.kodehighlighter.language.ocaml.rule.KeywordRule
import de.markusressel.kodehighlighter.language.ocaml.rule.ModuleRule
import de.markusressel.kodehighlighter.language.ocaml.rule.NumberRule
import de.markusressel.kodehighlighter.language.ocaml.rule.StringRule

class DarkBackgroundColorScheme : ColorScheme<CharacterStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<CharacterStyle>> {
        return when (type) {
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#86858B")) }
            }
            is BooleanRule,
            is KeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#AF38EE")) }
            }
            is ModuleRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#4B40E0")) }
            }
            is NumberRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#106BFF")) }
            }
            is StringRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FD8D0E")) }
            }
            else -> emptySet()
        }
    }

}