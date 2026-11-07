package de.markusressel.kodehighlighter.languages

import de.markusressel.kodehighlighter.language.c.CLanguage
import de.markusressel.kodehighlighter.language.cpp.CppLanguage
import de.markusressel.kodehighlighter.language.css.CssLanguage
import de.markusressel.kodehighlighter.language.diff.DiffLanguage
import de.markusressel.kodehighlighter.language.html.HtmlLanguage
import de.markusressel.kodehighlighter.language.javascript.JavaScriptLanguage
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRuleBook
import de.markusressel.kodehighlighter.language.json.JsonLanguage
import de.markusressel.kodehighlighter.language.kotlin.KotlinLanguage
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownLanguage
import de.markusressel.kodehighlighter.language.python.PythonLanguage
import de.markusressel.kodehighlighter.language.toml.TomlLanguage
import de.markusressel.kodehighlighter.language.xml.XmlLanguage
import de.markusressel.kodehighlighter.language.yaml.YamlLanguage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class KodeHighlighterLanguagesTest {
    private val detector = KodeHighlighterLanguages.detector

    @Test
    fun everyRuleBookTellsItsLanguage() {
        KodeHighlighterLanguages.all.forEach { language ->
            assertEquals(language, KodeHighlighterLanguages.ruleBookFor(language)?.language)
        }
    }

    @Test
    fun idsAndExtensionsAreLowerCaseAndIdsUnique() {
        val all = KodeHighlighterLanguages.all
        assertEquals(all.size, all.map { it.id }.toSet().size)
        all.forEach { language ->
            assertEquals(language.id.lowercase(), language.id)
            language.fileExtensions.forEach { assertEquals(it.lowercase(), it) }
            language.aliases.forEach { assertEquals(it.lowercase(), it) }
        }
    }

    @Test
    fun fileNames() {
        assertEquals(KotlinLanguage, detector.detectOne("build.gradle.kts"))
        assertEquals(KotlinLanguage, detector.detectOne("/sdcard/Projects/Main.KT"))
        assertEquals(PythonLanguage, detector.detectOne("C:\\code\\tool.py"))
        assertEquals(TomlLanguage, detector.detectOne("Cargo.lock"))
        assertEquals(YamlLanguage, detector.detectOne(".clang-format"))
        assertEquals(listOf(CLanguage, CppLanguage), detector.detect("stdio.h"))
        assertNull(detector.detectOne("notes.txt"))
        assertNull(detector.detectOne("Makefile"))
        assertNull(detector.detectOne(".kt"))
        assertNull(detector.detectOne("archive."))
    }

    @Test
    fun webLanguages() {
        assertEquals(HtmlLanguage, detector.detectOne("index.html"))
        assertEquals(HtmlLanguage, detector.detectOne(text = "<!DOCTYPE html>\n<html>"))
        assertEquals(HtmlLanguage, detector.detectOne(mimeType = "text/html"))
        assertEquals(CssLanguage, detector.detectOne("style.css"))
        assertEquals(JavaScriptLanguage, detector.detectOne("app.mjs"))
        assertEquals(JavaScriptLanguage, detector.detectOne(text = "#!/usr/bin/env node\n"))
        assertTrue(KodeHighlighterLanguages.ruleBookForName("js") is JavaScriptRuleBook)
        assertEquals(CssLanguage, KodeHighlighterLanguages.ruleBookForName("css")?.language)
        assertEquals(HtmlLanguage, KodeHighlighterLanguages.ruleBookForName("html")?.language)
    }

    @Test
    fun mimeTypes() {
        assertEquals(JsonLanguage, detector.detectOne(mimeType = "application/json; charset=utf-8"))
        assertEquals(XmlLanguage, detector.detectOne(mimeType = "image/svg+xml"))
    }

    @Test
    fun shebangs() {
        assertEquals(PythonLanguage, detector.detectOne(text = "#!/usr/bin/env python3\nprint(1)\n"))
        assertEquals(PythonLanguage, detector.detectOne(text = "#!/usr/bin/python3.12 -u\n"))
        assertEquals(PythonLanguage, detector.detectOne("tool", "#!/usr/bin/env -S python3 -u\n"))
        assertEquals(KotlinLanguage, detector.detectOne(text = "#!/usr/bin/env kotlin\n"))
        assertNull(detector.detectOne(text = "#!/bin/sh\necho hi\n"))
    }

    @Test
    fun firstLines() {
        assertEquals(XmlLanguage, detector.detectOne(text = "\uFEFF<?xml version=\"1.0\"?>\n<a/>"))
        assertEquals(DiffLanguage, detector.detectOne(text = "diff --git a/x b/x\n"))
        assertEquals(YamlLanguage, detector.detectOne(text = "%YAML 1.2\n---\na: 1\n"))
    }

    @Test
    fun modelinesWinOverTheFileName() {
        assertEquals(PythonLanguage, detector.detectOne("script.txt", "# vim: set ft=python :\n"))
        assertEquals(CppLanguage, detector.detectOne("vector.h", "// -*- mode: c++ -*-\n"))
        assertEquals(CppLanguage, detector.detectOne("vector.h", "/* -*- C++ -*- */\n"))
        val end = "x".repeat(5000) + "\n# vim: ft=yaml\n"
        assertEquals(YamlLanguage, detector.detectOne("config", end))
    }

    @Test
    fun theNameBeatsTheContent() {
        assertEquals(KotlinLanguage, detector.detectOne("Main.kt", "#!/usr/bin/env python3\n"))
        assertEquals(KotlinLanguage, detector.detect("Main.kt", "#!/usr/bin/env python3\n").first())
        assertTrue(PythonLanguage in detector.detect("Main.kt", "#!/usr/bin/env python3\n"))
    }

    @Test
    fun ids() {
        assertEquals(CppLanguage, detector.forId("CPP"))
        assertEquals(CppLanguage, detector.forId("c++"))
        assertNull(detector.forId("cobol"))
    }

    @Test
    fun ruleBooks() {
        assertTrue(KodeHighlighterLanguages.detectRuleBook("Main.kt") is KotlinRuleBook)
        assertNull(KodeHighlighterLanguages.detectRuleBook("notes.txt"))
    }

    @Test
    fun namesOfCodeBlocks() {
        assertTrue(KodeHighlighterLanguages.ruleBookForName("Kotlin") is KotlinRuleBook)
        assertTrue(KodeHighlighterLanguages.ruleBookForName("kts") is KotlinRuleBook)
        assertTrue(KodeHighlighterLanguages.ruleBookForName("yml")?.language == YamlLanguage)
        assertNull(KodeHighlighterLanguages.ruleBookForName("cobol"))
    }

    @Test
    fun markdownHighlightsCodeBlocksInTheirLanguage() {
        val markdown = KodeHighlighterLanguages.ruleBookFor(MarkdownLanguage)!!
        val text = "# A\n```kotlin\nfun main() {}\n```\n"
        val rules = runBlocking { markdown.createHighlighting(text) }.map { it.rule }
        assertTrue(rules.any { it::class.java.name.contains(".kotlin.") })
    }
}
