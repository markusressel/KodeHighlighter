# AGENTS.md

KodeHighlighter is an Android syntax highlighting library. It is published through JitPack, one artifact per module (`com.github.markusressel.KodeHighlighter:<module>`). KodeEditor builds on it.

## Layout

- `core`: package `de.markusressel.kodehighlighter.core`. `LanguageRuleBook` (list of rules, `createHighlighting`, `language`), `language` (`Language`, `LanguageDetector`), `rule` (`LanguageRule`, `RuleMatch`, `TokenType`, `RuleHelper.findRegexMatches`, `RuleHelper.findRegexGroupMatches`), `colorscheme` (`ColorScheme<Style>`, `withFallback`, `TokenTheme`, GitHub themes), `region` (`RegionScanner`, `RegionRuleBook`, `Embedding` for content in another language), `util` (highlighters for `Spannable`, `EditText` and `AnnotatedString`) and `ui` (Compose `KodeText`, `KodeTextField`, `KodeTextFieldColors`).
- One module per language: `c`, `cpp`, `csharp`, `dart`, `diff`, `go`, `java`, `json`, `kotlin`, `markdown`, `ocaml`, `properties`, `python`, `rust`, `swift`, `toml`, `xml`, `yaml`. Package `de.markusressel.kodehighlighter.language.<language>` with a `<Language>RuleBook`, a `<Language>Language` that the rule book returns as `language`, and a `rule` package. Only `java`, `json`, `kotlin`, `markdown`, `ocaml` and `python` have a `colorscheme` package, the others use a `TokenTheme`.
- Rules are `object`s implementing `LanguageRule`, with the regex in a `private val PATTERN` at the end of the object.
- Rules set `override val tokenType` so that a `TokenTheme` can style them. Comments and strings are found by a `RegionScanner` in `<Language>Regions`, the rule book extends `RegionRuleBook`. `properties` and `diff` have no regions and use a plain `LanguageRuleBook`.
- `languages`: depends on every language module. `KodeHighlighterLanguages` lists all languages, creates their rule books and has a `LanguageDetector` for all of them. Add new languages there.
- `app`: demo app, package `de.markusressel.kodehighlighter.demo`.
- `test-support`: unpublished test helpers (`assertHighlighting`) for use as `testImplementation`.
- `build-logic`: Gradle convention plugins. Versions live in `gradle/libs.versions.toml`.

## Build

- CI (`.github/workflows`) runs `make test` and `make apk` (`./gradlew app:assembleDebug`) and `make unittest` (`./gradlew testDebugUnitTest`, all modules) with JDK 21. JitPack also uses JDK 21.
- Unit tests exist in `core`, `test-support` and every language module (JUnit 4). Instrumented tests are in `app/src/androidTest` and are not run by CI. Tests in `app/src/sharedTest` run on both the JVM and a device, because their regex engines differ. Add new rule books to the lists in `RuleWorstCaseTest` and `TokenTypeCoverageTest`.
- Patterns must stay linear on long input. Prefer possessive quantifiers for group loops, since the JVM recurses on them, and let an unterminated construct end at the end of the text.

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
