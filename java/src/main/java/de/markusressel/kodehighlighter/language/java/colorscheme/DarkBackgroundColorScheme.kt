package de.markusressel.kodehighlighter.language.java.colorscheme

import android.graphics.Color
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.java.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.java.rule.ClassKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.CommentRule
import de.markusressel.kodehighlighter.language.java.rule.FinalKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ImportKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.PackageKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.ReturnKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.StaticKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.TypeKeywordRule
import de.markusressel.kodehighlighter.language.java.rule.VisibilityKeywordRule
import java.util.Collections.emptySet

/**
 * A dark color scheme for java
 */
class DarkBackgroundColorScheme : ColorScheme<CharacterStyle> {

    override fun getStyles(type: LanguageRule): Set<StyleFactory<CharacterStyle>> {
        return when (type) {
            is ImportKeywordRule,
            is PackageKeywordRule,
            is ClassKeywordRule,
            is TypeKeywordRule,
            is StaticKeywordRule,
            is ReturnKeywordRule,
            is FinalKeywordRule,
            is VisibilityKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FF6D00")) }
            }
            is AnnotationRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FBC02D")) }
            }
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#33691E")) }
            }
            else -> emptySet()
        }
    }

}