package de.markusressel.kodehighlighter.language_detection

import org.tensorflow.lite.experimental.GpuDelegate

object GpuDelegateHelper {

    /** Checks whether `GpuDelegate` is available. */
    val isGpuDelegateAvailable: Boolean
        get() {
            return try {
                Class.forName("org.tensorflow.lite.experimental.GpuDelegate")
                true
            } catch (e: Exception) {
                false
            }
        }

    /** Returns an instance of `GpuDelegate` if available. */
    fun createGpuDelegate(): GpuDelegate {
        try {
            return Class.forName("org.tensorflow.lite.experimental.GpuDelegate")
                    .asSubclass<GpuDelegate>(GpuDelegate::class.java)
                    .getDeclaredConstructor()
                    .newInstance()
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
    }

}