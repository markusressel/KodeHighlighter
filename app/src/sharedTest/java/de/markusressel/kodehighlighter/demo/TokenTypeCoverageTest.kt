package de.markusressel.kodehighlighter.demo

import de.markusressel.kodehighlighter.language.diff.DiffRuleBook
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.properties.PropertiesRuleBook
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import de.markusressel.kodehighlighter.language.dart.DartRuleBook
import de.markusressel.kodehighlighter.language.swift.SwiftRuleBook
import de.markusressel.kodehighlighter.language.rust.RustRuleBook
import de.markusressel.kodehighlighter.language.go.GoRuleBook
import de.markusressel.kodehighlighter.language.csharp.CSharpRuleBook
import de.markusressel.kodehighlighter.language.cpp.CppRuleBook
import de.markusressel.kodehighlighter.language.c.CRuleBook
import de.markusressel.kodehighlighter.language.toml.TomlRuleBook
import de.markusressel.kodehighlighter.language.xml.XmlRuleBook
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook
import de.markusressel.kodehighlighter.language.css.CssRuleBook
import de.markusressel.kodehighlighter.language.generic.GenericRuleBook
import de.markusressel.kodehighlighter.language.html.HtmlRuleBook
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRuleBook
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
            CRuleBook(),
            CppRuleBook(),
            CSharpRuleBook(),
            GoRuleBook(),
            RustRuleBook(),
            SwiftRuleBook(),
            DartRuleBook(),
            PythonRuleBook(),
            JavaScriptRuleBook(),
            CssRuleBook(),
            HtmlRuleBook(),
            GenericRuleBook(),
        ).flatMap { it.getRules() }
            .filter { it.tokenType == null }
            .map { it::class.java.name }

        assertEquals(emptyList<String>(), rulesWithoutType)
    }
}
