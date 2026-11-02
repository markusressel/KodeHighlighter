package de.markusressel.kodehighlighter.demo

import de.markusressel.kodehighlighter.language.diff.DiffRuleBook
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.properties.PropertiesRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import de.markusressel.kodehighlighter.language.toml.TomlRuleBook
import de.markusressel.kodehighlighter.language.xml.XmlRuleBook
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook
import org.junit.Assert.assertEquals
import org.junit.Test

class TokenTypeCoverageTest {

    @Test
    fun allRulesDefineATokenType() {
        val rulesWithoutType = listOf(
            JavaRuleBook(),
            JsonRuleBook(),
            KotlinRuleBook(),
            MarkdownRuleBook(),
            OCamlRuleBook(),
            XmlRuleBook(),
            YamlRuleBook(),
            TomlRuleBook(),
            PropertiesRuleBook(),
            DiffRuleBook(),
            PythonRuleBook(),
        ).flatMap { it.getRules() }
            .filter { it.tokenType == null }
            .map { it::class.java.name }

        assertEquals(emptyList<String>(), rulesWithoutType)
    }
}
