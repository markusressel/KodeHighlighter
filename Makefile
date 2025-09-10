

test:
	./gradlew app:assembleDebug --stacktrace

unittest:
	./gradlew core:testDebugUnitTest --stacktrace

apk:
	./gradlew app:assembleDebug --stacktrace

app: apk
build: apk

publish:
	./gradlew publishMavenPublicationToMavenLocal