package de.markusressel.kodehighlighter.language.xml

import de.markusressel.kodehighlighter.language.xml.rule.AttributeRule
import de.markusressel.kodehighlighter.language.xml.rule.AttributeValueRule
import de.markusressel.kodehighlighter.language.xml.rule.CDataRule
import de.markusressel.kodehighlighter.language.xml.rule.CommentRule
import de.markusressel.kodehighlighter.language.xml.rule.DoctypeRule
import de.markusressel.kodehighlighter.language.xml.rule.EntityRule
import de.markusressel.kodehighlighter.language.xml.rule.TagRule
import de.markusressel.kodehighlighter.testsupport.assertHighlighting
import org.junit.Test

class XmlRuleBookTest {

    private val ruleBook = XmlRuleBook()

    @Test
    fun `tags attributes and values`() {
        assertHighlighting(
            ruleBook,
            "<a b=\"c\" d:e='f'>g</a>",
            TagRule to listOf("<a", ">", "</a", ">"),
            AttributeRule to listOf("b", "d:e"),
            AttributeValueRule to listOf("\"c\"", "'f'"),
        )
    }

    @Test
    fun `self closing and declaration`() {
        assertHighlighting(
            ruleBook,
            "<?xml version=\"1.0\"?><b/>",
            TagRule to listOf("<?xml", "?>", "<b", "/>"),
            AttributeRule to listOf("version"),
            AttributeValueRule to listOf("\"1.0\""),
        )
    }

    @Test
    fun `comments hide tags`() {
        assertHighlighting(
            ruleBook,
            "<!-- <a b=\"c\"> --><d>",
            CommentRule to listOf("<!-- <a b=\"c\"> -->"),
            TagRule to listOf("<d", ">"),
        )
    }

    @Test
    fun `cdata hides tags`() {
        assertHighlighting(
            ruleBook,
            "<![CDATA[<a>]]>",
            CDataRule to listOf("<![CDATA[<a>]]>"),
        )
    }

    @Test
    fun `entities and doctype`() {
        assertHighlighting(
            ruleBook,
            "<!DOCTYPE html>&amp; &#10; &x",
            DoctypeRule to listOf("<!DOCTYPE html>"),
            TagRule to listOf(">"),
            EntityRule to listOf("&amp;", "&#10;"),
        )
    }

    @Test
    fun `text is not a tag`() {
        assertHighlighting(
            ruleBook,
            "a < b",

        )
    }
}
