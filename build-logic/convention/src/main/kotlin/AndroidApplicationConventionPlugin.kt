import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import de.markusressel.kodehighlighter.configureKotlinAndroid
import de.markusressel.kodehighlighter.configureKotlinAndroidToolchain
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            group = "de.markusressel.kodehighlighter.demo"

            configureKotlinAndroidToolchain()
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
            }
        }
    }

}