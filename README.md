# KodeHighlighter
Simple, extendable code highlighting for Spannables on Android.

# Build Status

| Master |
|--------|
| [![Master](https://travis-ci.org/markusressel/KodeHighlighter.svg?branch=master)](https://travis-ci.org/markusressel/KodeHighlighter/branches) |
| [![codebeat badge](https://codebeat.co/badges/e533d507-9e49-4010-9c02-7fb3e638bb0d)](https://codebeat.co/projects/github-com-markusressel-kodehighlighter-master) |

# How to use
Have a look at the demo app (`app`  module) for a complete sample.

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
the `EditTextSyntaxHighlighter` class can be used:

```kotlin
CoroutineScope(Dispatchers.Main).launch {
    val editTextHighlighter = EditTextHighlighter(
            target = yourEditTextView,
            languageRuleBook = MarkdownRuleBook())
    editTextHighlighter.start()

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

    def codeHighlighterVersion = "v1.2.2"
    implementation("com.github.markusressel.KodeHighlighter:core:${codeHighlighterVersion}")
}
```

in your desired module ```build.gradle``` file.


## Syntax highlighting

### Language Autodetection

Currently there is no auto detection for the language used in a text.

### Integrated language rule books

This library includes rule books for a small set of languages for you to use right away, without
spending time to think about finding the right tokens and color schemes:

* java
* json
* kotlin
* markdown
* python

To include one of them in your project, just pick the ones you would like to use
and - **in addition** to the `core` module - import them as a dependency:

```groovy
dependencies {
    ...

    implementation("com.github.markusressel.KodeHighlighter:java:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:json:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:kotlin:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:markdown:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:python:${codeHighlighterVersion}")
    [etc.]
```

### Writing a custom rule book

A `LanguageRuleBook` consists of a **default color scheme** and a **set of rules** that provide
information on how to style different parts of the `Spannable`. Have a look at how the
[MarkdownSyntaxHighlighter](markdown/src/main/java/de/markusressel/kodehighlighter/language/markdown/MarkdownRuleBook.kt)
is implemented to get a feel for how to implement it yourself.

### Styling

Different highlighting styles for a given language can be achieved
by implementing the `ColorScheme` interface and passing it to the highlighter
(f.ex. a `SpannableHighlighter`). For more info on how to do this have a look at the
[DarkBackgroundColorScheme from the Markdown rule book](markdown/src/main/java/de/markusressel/kodehighlighter/language/markdown/colorscheme/DarkBackgroundColorScheme.md).

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
