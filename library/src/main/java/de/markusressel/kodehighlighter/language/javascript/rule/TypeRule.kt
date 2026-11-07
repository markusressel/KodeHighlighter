package de.markusressel.kodehighlighter.language.javascript.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

/**
 * Built-in classes, and the classes after `class`, `extends` and `new`
 */
object TypeRule : LanguageRule {

    override val tokenType = TokenType.TYPE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        // each alternative of the pattern has its own group
        val matcher = PATTERN.toPattern().matcher(text)
        val matches = mutableListOf<RuleMatch>()
        while (matcher.find()) {
            val group = (1..matcher.groupCount()).first { matcher.start(it) >= 0 }
            matches += RuleMatch(matcher.start(group), matcher.end(group))
        }
        return matches
    }

    private val PATTERN = "(?<![\\w\$.])(Array|ArrayBuffer|BigInt|BigInt64Array|BigUint64Array|Boolean|DataView|Date|Error|EvalError|Float32Array|Float64Array|Function|Int8Array|Int16Array|Int32Array|Intl|JSON|Map|Math|Number|Object|Promise|Proxy|RangeError|ReferenceError|Reflect|RegExp|Set|String|Symbol|SyntaxError|TypeError|URIError|Uint8Array|Uint8ClampedArray|Uint16Array|Uint32Array|WeakMap|WeakRef|WeakSet)(?![\\w\$])|\\b(?:class|extends|new)[ \\t]++([A-Za-z_\$][\\w\$]*)".toRegex()

}
