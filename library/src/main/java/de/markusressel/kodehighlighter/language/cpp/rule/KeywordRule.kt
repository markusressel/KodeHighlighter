package de.markusressel.kodehighlighter.language.cpp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeywordRule : LanguageRule {

    override val tokenType = TokenType.KEYWORD

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:auto|break|case|const|constexpr|continue|default|do|else|enum|extern|for|goto|if|inline|register|restrict|return|sizeof|static|struct|switch|typedef|typeof|union|volatile|while|_Alignas|_Alignof|_Atomic|_Generic|_Noreturn|_Static_assert|_Thread_local|alignas|alignof|static_assert|thread_local|class|namespace|template|typename|public|private|protected|virtual|new|delete|this|try|catch|throw|using|operator|friend|explicit|mutable|noexcept|consteval|constinit|decltype|dynamic_cast|static_cast|const_cast|reinterpret_cast|concept|requires|co_await|co_return|co_yield|typeid)\\b|\\b(?:override|final)(?=\\s*[;{])".toRegex()

}
