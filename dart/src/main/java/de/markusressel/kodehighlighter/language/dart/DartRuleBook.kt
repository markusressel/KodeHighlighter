package de.markusressel.kodehighlighter.language.dart

import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.region.RegionRuleBook
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.dart.rule.AnnotationRule
import de.markusressel.kodehighlighter.language.dart.rule.CommentRule
import de.markusressel.kodehighlighter.language.dart.rule.KeywordRule
import de.markusressel.kodehighlighter.language.dart.rule.LiteralRule
import de.markusressel.kodehighlighter.language.dart.rule.NumberRule
import de.markusressel.kodehighlighter.language.dart.rule.StringRule
import de.markusressel.kodehighlighter.language.dart.rule.TypeRule

/**
 * Dart language rule book
 */
open class DartRuleBook : RegionRuleBook() {

    override val language: Language get() = DartLanguage

    override val scanner = DartRegions.scanner

    private val rules: List<LanguageRule> = listOf(
        AnnotationRule,
        KeywordRule,
        TypeRule,
        LiteralRule,
        NumberRule,
        StringRule,
        CommentRule,
    )

    override fun getRules() = rules

}
