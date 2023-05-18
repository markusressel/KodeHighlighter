import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPublishingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                with(pluginManager) {
                    apply("maven-publish")
                }
                publishing {
                    singleVariant("release") {
                        group = "com.github.markusressel.KodeHighlighter"
                        withJavadocJar()
                        withSourcesJar()
                    }
                }
            }
        }
    }
}