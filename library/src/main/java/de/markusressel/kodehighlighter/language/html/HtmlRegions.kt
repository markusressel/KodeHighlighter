package de.markusressel.kodehighlighter.language.html

import de.markusressel.kodehighlighter.core.region.DelimitedRegion
import de.markusressel.kodehighlighter.core.region.Embedding
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.css.CssRuleBook
import de.markusressel.kodehighlighter.language.html.rule.EmbeddingTagRule
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRuleBook
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule

/**
 * Comments of HTML, and the content of `script` and `style` elements, which is JavaScript and CSS
 */
internal object HtmlRegions {

    private val javaScript = JavaScriptRuleBook()
    private val css = CssRuleBook()

    val scanner = RegionScanner(
        listOf(
            DelimitedRegion(CommentRule, "<!--", "-->", multiLine = true),
            PatternRegion(EmbeddingTagRule, ">", content("script"), Embedding(contentGroup = 1) { javaScript }),
            PatternRegion(EmbeddingTagRule, ">", content("style"), Embedding(contentGroup = 1) { css }),
        )
    )

    /** @return a regex from the `>` of a start tag, not a self-closing one, to its end tag or the end of the text */
    private fun content(tag: String) =
        "(?i)(?<=<$tag(?:\\s[^>]{0,1000})?(?<!/))>([\\s\\S]*?)(?=</$tag\\s*>|\\z)".toRegex()

}
