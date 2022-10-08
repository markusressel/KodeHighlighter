

test:
	./gradlew app:assembleDebug core:dokkaJavadoc java:dokkaJavadoc json:dokkaJavadoc kotlin:dokkaJavadoc markdown:dokkaJavadoc python:dokkaJavadoc --stacktrace

unittest:
	./gradlew core:testDebugUnitTest --stacktrace

apk:
	./gradlew app:assembleDebug --stacktrace
