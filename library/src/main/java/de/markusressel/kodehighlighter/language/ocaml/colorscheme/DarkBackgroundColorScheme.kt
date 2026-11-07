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
                setOf { ForegroundColorSpan(Color.parseColor("#CE93D8")) }
            }
            is ModuleRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#8C9EFF")) }
            }
            is NumberRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#448AFF")) }
            }
            is StringRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FD8D0E")) }
            }
            else -> emptySet()
        }
    }

}