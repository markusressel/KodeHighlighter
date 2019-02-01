package de.markusressel.kodehighlighter.language_detection

import android.content.Context
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

class LanguageClassifier(context: Context) {

    private val tensorFlowInferenceInterface = TensorFlowInferenceInterface(context.assets, MODEL_ASSET_FILE_NAME)

    companion object {
        private const val MODEL_ASSET_FILE_NAME = "file:///android_asset/language_model.pb"
        private const val LABEL_ASSET_FILE_NAME = "file:///android_asset/language_label.txt"

        private const val INPUT_NAME = "input"
        private const val OUTPUT_NAME = "output"
    }

}