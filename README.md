# KodeHighlighter
Simple, extendable code highlighting for Spannables on Android.

# Build Status

| Master |
|--------|
| [![Master](https://travis-ci.org/markusressel/KodeHighlighter.svg?branch=master)](https://travis-ci.org/markusressel/KodeHighlighter/branches) |
| [![codebeat badge](https://codebeat.co/badges/e533d507-9e49-4010-9c02-7fb3e638bb0d)](https://codebeat.co/projects/github-com-markusressel-kodehighlighter-master) |

# How to use
Have a look at the demo app (`app`  module) for a complete sample.

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

    def codeHighlighterVersion = "v1.2.0"
    implementation("com.github.markusressel.KodeHighlighter:core:${codeHighlighterVersion}")
}
```

in your desired module ```build.gradle``` file.


## Syntax highlighting

### Language Autodetection

Currently there is no auto detection for the language used in a text.

### Integrated syntax highlighters

This library includes a small set of highlighters for you to use right away without spending time to think about the right code highlighting.
Here you can find a list of those items:

* markdown
* java
* kotlin

To include an existing language just pick the ones you would like to use and import them **in addition** to the `core` module:

```groovy
dependencies {
    ...
    implementation("com.github.markusressel.KodeHighlighter:markdown:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:java:${codeHighlighterVersion}")
    implementation("com.github.markusressel.KodeHighlighter:kotlin:${codeHighlighterVersion}")
    [etc.]
```

### Writing a custom syntax highlighter

Using your own rules to highlight text in the editor can be achieved by extending the `StatefulSyntaxHighlighter` class (which implements the `SyntaxHighlighter` interface):

```kotlin
class MarkdownSyntaxHighlighter : SyntaxHighlighter {

    override var colorScheme: SyntaxColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<SyntaxHighlighterRule> {
        return setOf(HeadingRule(), ItalicRule(), BoldRule(), CodeInlineRule(), CodeLineRule(), TextLinkRule(), ImageLinkRule(), StrikeRule())
    }

}
```

A syntax highlighter consists of a **default color scheme** and a **set of rules** that provide information on how to style different parts of the `Spannable`.
Have a look at how the `MarkdownSyntaxHighlighter` is implemented to get a feel for how to implement those methods.

## Styling

Different code highlighting styles for the same language can be achieved
by implementing the `SyntaxColorScheme` interface.

This is taken from the `markdown` highlighter:
```kotlin
class DarkBackgroundColorScheme : SyntaxColorScheme {

    override fun getStyles(type: SyntaxHighlighterRule): Set<StyleFactory> {
        return when (type) {
            is BoldRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#0091EA")) },
                        { StyleSpan(Typeface.BOLD) })
            }
            is ItalicRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#0091EA")) }, { StyleSpan(Typeface.ITALIC) })
            }
            is CodeInlineRule, is CodeLineRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#00C853")) })
            }
            is HeadingRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#FF6D00")) })
            }
            is ImageLinkRule, is TextLinkRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#7C4DFF")) })
            }
            is StrikeRule -> {
                setOf({ ForegroundColorSpan(Color.parseColor("#5D4037")) })
            }
            else -> emptySet()
        }
    }

}
```

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