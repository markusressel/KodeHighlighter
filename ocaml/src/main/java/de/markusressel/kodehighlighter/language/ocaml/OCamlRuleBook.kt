package de.markusressel.kodehighlighter.language.ocaml

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.language.ocaml.colorscheme.DarkBackgroundColorScheme
import de.markusressel.kodehighlighter.language.ocaml.rule.*

class OCamlRuleBook : LanguageRuleBook {

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): List<LanguageRule> {
        return listOf(
            BooleanRule(),
            KeywordRule(),
            ModuleRule(),
            NumberRule(),
            CommentRule(),
            StringRule())
    }

}