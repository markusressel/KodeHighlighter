package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeywordRule : LanguageRule {

    override val tokenType = TokenType.KEYWORD

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:and|as|bool|boolean|break|case|catch|char|class|const|continue|def|delete|do|double|echo|elif|else|elseif|except|exit|extends|finally|float|fn|for|foreach|func|function|global|if|import|in|int|integer|interface|let|long|new|not|or|print|private|protected|public|return|self|signed|static|string|struct|switch|then|this|throw|try|unsigned|var|void|while|yield)\\b".toRegex()

}
