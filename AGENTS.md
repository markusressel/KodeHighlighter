# AGENTS.md

KodeHighlighter is an Android syntax highlighting library. It is published through JitPack, one artifact per module (`com.github.markusressel.KodeHighlighter:<module>`). KodeEditor builds on it.

## Layout

- `core`: package `de.markusressel.kodehighlighter.core`. `LanguageRuleBook` (list of rules, `createHighlighting`), `rule` (`LanguageRule`, `RuleMatch`, `RuleHelper.findRegexMatches`), `colorscheme` (`ColorScheme<Style>`), `util` (highlighters for `Spannable`, `EditText` and `AnnotatedString`) and `ui` (Compose `KodeText`, `KodeTextField`, `KodeTextFieldColors`).
- One module per language: `java`, `json`, `kotlin`, `markdown`, `ocaml`, `python`. Package `de.markusressel.kodehighlighter.language.<language>` with a `<Language>RuleBook`, a `rule` package and a `colorscheme` package.
- Rules are `object`s implementing `LanguageRule`, with the regex in a `private val PATTERN` at the end of the object.
- `app`: demo app, package `de.markusressel.kodehighlighter.demo`.
- `build-logic`: Gradle convention plugins. Versions live in `gradle/libs.versions.toml`.

## Build

- CI (`.github/workflows`) runs `make test` and `make apk` (`./gradlew app:assembleDebug`) and `make unittest` (`./gradlew core:testDebugUnitTest`) with JDK 21. JitPack also uses JDK 21.
- Unit tests exist in `markdown` and `app` (JUnit 4).

## Code style

- Kotlin, 4 spaces.
- Composables: `modifier: Modifier = Modifier` first, required parameters, then optional parameters with defaults. Slots like `decorationBox` go last.
- Public API has a short KDoc with one `@param` line per parameter, lower case, no trailing period.
- Compose colors follow the Material pattern: an interface with `@Composable fun xColor(): State<Color>` and a `...Defaults` object.
- Most composable files end with a private `@Preview` function.
- New parameters of public functions need defaults, so existing consumers keep compiling.

## Commits and PRs

- Commit messages are short and lower case, e.g. `open rulebooks`, `reduce instance creations`. Releases are commits named after the version, e.g. `v4.0.4`.
- Dependency updates come from dependabot. Don't bump dependencies in feature PRs.
