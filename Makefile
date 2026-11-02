

test:
	./gradlew app:assembleDebug --stacktrace

unittest:
	./gradlew testDebugUnitTest --stacktrace

apk:
	./gradlew app:assembleDebug --stacktrace

app: apk
build: apk

publish:
	./gradlew publishMavenPublicationToMavenLocal