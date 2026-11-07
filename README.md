# KodeHighlighter  [![Build](https://github.com/markusressel/KodeHighlighter/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/markusressel/KodeHighlighter/actions/workflows/build.yml)
Simple, extendable code highlighting for Spannables on Android.

# Features

* [x] Compose Support (`SpanStyle`)
* [x] View (Legacy) Support (`CharacterStyle`)
* [x] Custom Languages
* [x] Language specific Themes
* [x] Themes that work across languages (GitHub Dark and GitHub Light)
* [x] Comments and strings found by a linear scanner instead of regular expressions
* [x] Highlighting off the main thread

# How to use

Have a look at the demo app (`app`  module) for a complete sample.

# Compose

## KodeText

```kotlin
val text by remember(selectedLanguage) {
    mutableStateOf("# Hello World!")
}
val ruleBook by remember { derivedStateOf { MarkdownRuleBook() } }
val colorScheme by remember { derivedStateOf { de.markusressel.kodehighlighter.language.markdown.colorscheme.DarkBackgroundColorSchemeWithSpanStyle() } }

KodeText(
    modifier = Modifier
        .padding(4.dp)
        .wrapContentSize()
        .fillMaxWidth(),
    text = text,
    languageRuleBook = ruleBook,
    colorScheme = colorScheme,
    textColor = MaterialTheme.colors.onSurface,
)
```

## KodeTextField

```kotlin
val initialText = "# Hello World!"
var textFieldValue by remember(initialText) {
    mutableStateOf(
        TextFieldValue(
            annotatedString = AnnotatedString(initialText),
        )
    )
}

KodeTextField(
    modifier = Modifier
        .padding(4.dp)
        .wrapContentSize()
        .fillMaxWidth(),
    value = textFieldValue,
    languageRuleBook = ruleBook,
    colorScheme = colorScheme,
    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colors.onSurface),
    onValueChange = {
        if (it != textFieldValue) {
            textFieldValue = it
        }
    }
)
```

# XML

## Simple example

```kotlin
val markdownText = readResourceFileAsText(R.raw.markdown_sample)
val markdownRuleBook = MarkdownRuleBook()
val markdownHighlighter = SpannableHighlighter(markdownRuleBook, DarkBackgroundColorScheme())

CoroutineScope(Dispatchers.Main).launch {
    val spannable = withContext(Dispatchers.Default) {
        val spannable = createSpannable(text)
        markdownHighlighter.highlight(spannable)
        spannable
    }
    target.text = spannable
}
```

## Working with `EditText`
When using this library with an `EditText` view, previously applied styles need to be removed
when the highlighting is updated due to a text change. To make it easy for you to deal with this
the `EditTextHighlighter` class can be used:

```kotlin
val editTextHighlighter = EditTextHighlighter(
        target = editTextMarkdownDark,
        languageRuleBook = MarkdownRuleBook())
editTextHighlighter.start()

CoroutineScope(Dispatchers.Main).launch {
    val markdown = withContext(Dispatchers.IO) {
        readResourceFileAsText(R.raw.markdown_sample)
    }
    editTextMarkdownDark.setText(markdown)
}
```

You can then set or edit any text you like and the highlighting will refresh automatically.

## Gradle
To use this library just include it in your dependencies using

```groovy
repositories {
    ...
    maven { url "https://jitpack.io" }
}
```

in your project build.gradle file and

```groovy
dependencies {
    ...

    implementation("com.github.markusressel:KodeHighlighter:7.0.1")
}
```

in your desired module ```build.gradle``` file. This one artifact contains every language. R8 removes the languages
that your app does not use.

Up to 6.2.0, there was one artifact per language, like `com.github.markusressel.KodeHighlighter:markdown`. Replace all
of them with the one above, the packages and classes are the same.


## Syntax highlighting

### Language Autodetection

Each language has a `Language`, like `KotlinLanguage`, with a stable `id` (the one of VS Code, like `kotlin`
or `cpp`), its file extensions, file names, MIME types, aliases, shebang interpreters and first lines. Its rule book
returns it as `language`, so other code can pick what fits the language, for example the folding of an editor.

`KodeHighlighterLanguages` contains all languages and finds the one of a file:

```kotlin
// a new rule book for the most likely language, null if none fits
val ruleBook = KodeHighlighterLanguages.detectRuleBook(fileName = "build.gradle.kts", text = text)

// all languages that fit, the most likely first
val candidates: List<Language> = KodeHighlighterLanguages.detector.detect(fileName = "vector.h", text = text)
```

A modeline in the text (`vim: ft=python`, `-*- mode: c++ -*-`) counts most, then the whole file name, its extension,
the MIME type, a shebang like `#!/usr/bin/env python3` and the start of the first line, like `<?xml`. An extension that
several languages share, like `h` for C and C++, gives all of them. A `LanguageDetector` also works with your own
list of languages.

### Integrated language rule books

This library includes rule books for a set of languages for you to use right away, without
spending time to think about finding the right tokens and color schemes:

* c
* cpp
* csharp
* css
* dart
* diff
* go
* html, with `script` elements highlighted as JavaScript and `style` elements as CSS
* java
* javascript
* json
* kotlin
* markdown, with YAML front matter and fenced code blocks highlighted in their language
* ocaml
* properties
* python
* rust
* swift
* toml
* xml
* yaml

All of them are in the artifact, use their rule books like `KotlinRuleBook()` directly.

For other languages, `GenericRuleBook` highlights what many languages share: `//`, `#` and `/* */` comments, strings,
numbers, function names and common keywords. It has no language, so detection never picks it, use it as a fallback:

```kotlin
val ruleBook = KodeHighlighterLanguages.detectRuleBook(fileName, text) ?: GenericRuleBook()
```

### Languages in other languages

Markdown highlights YAML front matter as YAML, and fenced code blocks in their language, by the name in the info
string, like `kotlin` in ```` ```kotlin ````. Blocks without a language, or with an unknown one, stay one code block.
You can choose the rule books yourself:

```kotlin
val markdown = MarkdownRuleBook { name -> if (name == "kotlin") KotlinRuleBook() else null }
// no code blocks in their language
val plain = MarkdownRuleBook { null }
```

Blocks that did not change since the last highlighting are not highlighted again, so a long text with many code blocks
stays fast while typing.

Your own `RegionRuleBook` can embed other languages the same way: an `Embedding` on a `PatternRegion` names the group
of its regex that holds the content and returns the rule book for it. The rest of the region, like its delimiters,
keeps the rule of the region.

Rules of embedded languages need colors too. A `TokenTheme` like `GitHubDarkTheme` or `GitHubLightTheme` styles every
rule with a token type, and `colorScheme.withFallback(GitHubDarkTheme.spanStyleColorScheme)` adds it to a color scheme
of one language, like the Markdown color schemes, which only style Markdown. KodeEditor does that by itself, with the
theme that fits the brightness of its background.

### Writing a custom rule book

A `LanguageRuleBook` consists of a **set of rules** that find the parts of a text to style.
Rules should set a `tokenType`, so that a `TokenTheme` can style them. Override `language` to tell which language it
highlights. Have a look at how the
[MarkdownRuleBook](markdown/src/main/java/de/markusressel/kodehighlighter/language/markdown/MarkdownRuleBook.kt)
is implemented to get a feel for how to implement it yourself.

### Styling

Different highlighting styles for a given language can be achieved
by implementing the `ColorScheme` interface and passing it to the highlighter
(f.ex. a `SpannableHighlighter`). For more info on how to do this have a look at the
[DarkBackgroundColorScheme from the Markdown rule book](markdown/src/main/java/de/markusressel/kodehighlighter/language/markdown/colorscheme/DarkBackgroundColorScheme.kt).

`GitHubDarkTheme` and `GitHubLightTheme` style every language by its token types.
Modules without a color scheme of their own, like `rust` or `yaml`, are used with these themes:

```kotlin
KodeText(
    text = text,
    languageRuleBook = RustRuleBook(),
    colorScheme = GitHubDarkTheme.spanStyleColorScheme,
    textColor = Color(GitHubDarkTheme.foreground),
)
```

Use `GitHubDarkTheme.characterStyleColorScheme` with a `SpannableHighlighter` or `EditTextHighlighter`.

# Contributing

GitHub is for social coding: if you want to write code, I encourage contributions through pull requests from forks
of this repository. Create GitHub tickets for bugs and new features and comment on the ones that you are interested in.

# License

```
MIT License

Copyright (c) 2018 Markus Ressel

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
