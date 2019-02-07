package de.markusressel.kodehighlighter.language_detection

import android.content.Context
import android.content.res.AssetManager
import org.tensorflow.lite.Delegate
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


/**
 * Initializes a native TensorFlow session for classifying images.
 *
 * Ideally, inputSize could have been retrieved from the shape of the input operation.  Alas,
 * the placeholder node for input in the graphdef typically used does not specify a shape, so it
 * must be passed in as a parameter.
 *
 * @param modelFilename The filepath of the model GraphDef protocol buffer.
 * @param labelFilename The filepath of label file for classes.
 * @param inputName     The label of the image input node.
 * @param outputName    The label of the output node.
 * @throws IOException
 */
class LanguageClassifier(context: Context,
                         val modelFilename: String = DEFAULT_MODEL_ASSET_FILE_PATH,
                         val labelFilename: String = DEFAULT_LABEL_ASSET_FILE_NAME,
                         val inputName: String = INPUT_NAME,
                         val outputName: String = OUTPUT_NAME,
                         val useGpu: Boolean = false) {

    private val assetManager: AssetManager = context.assets

    /** Options for configuring the Interpreter. */
    private val tfliteOptions = Interpreter.Options()

    /** The loaded TensorFlow Lite model. */
    private var tfliteModel: MappedByteBuffer

    /** An instance of the driver class to run model inference with Tensorflow Lite. */
    private var tflite: Interpreter

    /** holds a gpu delegate */
    private var gpuDelegate: Delegate? = null

    // Pre-allocated buffers.
    private val labelList = loadLabelList()
    private val outputs: FloatArray

    init {
        tfliteModel = loadModelFile()

        if (useGpu && gpuDelegate == null && GpuDelegateHelper.isGpuDelegateAvailable) {
            gpuDelegate = GpuDelegateHelper.createGpuDelegate()
            tfliteOptions.addDelegate(gpuDelegate)
        }

        tflite = Interpreter(tfliteModel as ByteBuffer, tfliteOptions)

        // Pre-allocate buffers.
        outputs = FloatArray(labelList.size)
    }

    /**
     * Loads the list of labels that correspond to the output of the model
     */
    private fun loadLabelList(): List<String> {
        return assetManager.open(getLabelPath()).bufferedReader().readLines()
    }

    /**
     * @return the path to the file containing output labels
     */
    private fun getLabelPath(): String {
        return DEFAULT_LABEL_ASSET_FILE_NAME
    }

    /**
     * Loads the model file into a buffer
     */
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(getModelPath())
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun getModelPath(): String {
        return DEFAULT_MODEL_ASSET_FILE_PATH
    }

    /**
     * Recognize the language of the given snippet
     *
     * @param snippet the code snipped to analyze
     * @return a list of [Recognition] objects
     */
    fun recognizeLanguage(snippet: String, confidenceThreshold: Float = Float.MIN_VALUE): List<Recognition> {
        // TODO: input to ByteBuffer??
        val input: ByteArray = snippetToVector(snippet)
        val inferenceResults: FloatArray = runInference(input)

        return labelList.mapIndexed { index, label ->
            Recognition("$index", label, inferenceResults[index])
        }.filter {
            it.confidence >= confidenceThreshold
        }.sortedByDescending {
            it.confidence
        }.take(MAX_RESULTS)
    }

    /**
     * Extracts feature vector from text
     *
     * @param text text
     * @param vectorSize size of the vector
     * @param normalizeWhitespace: replacing all whitespace to space
     *
     * @return: vector
     */
    private fun snippetToVector(text: String, vectorSize: Int = 2 * 1024, normalizeWhitespace: Boolean = false): ByteArray {
        val result = mutableListOf<Byte>()

        var inputText = text

        // Normalising whitespace
        // NOTE: this could backfire due to whitespace significant languages
        // but allows for more code consumed
        if (normalizeWhitespace) {
            listOf('\n', '\r', '\t').forEach {
                inputText = inputText.replace(it, ' ')
            }
        }

        inputText = inputText.replace("\\s+", " ")

        inputText = inputText.take(vectorSize)
        inputText.forEach {
            if (it in CHARACTER_TO_VECTOR_MAP) {
                result.addAll(CHARACTER_TO_VECTOR_MAP[it]!!)
            }
        }

        if (result.size < vectorSize) {
            for (j in 0..(vectorSize - result.size)) {
                result.addAll(PAD_VECTOR)
            }
        }

        return result.toByteArray()
    }

    private fun runInference(input: ByteArray): FloatArray {
        tflite.run(input, outputs)
        return outputs
    }

    companion object {
        /**
         * Qunatization according to LeCun paper (2016): "Text Understanding from Scratch"
         *
         * 70 characters including space
         */
        private const val SUPPORTED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789-,;.!?:'/\\|_@#$%^&*~`+-=<>()[]{}\" "
        private val PAD_VECTOR = ByteArray(SUPPORTED_CHARACTERS.length).toTypedArray()
        private val CHARACTER_TO_VECTOR_MAP: MutableMap<Char, Array<Byte>> = mutableMapOf<Char, Array<Byte>>().apply {
            SUPPORTED_CHARACTERS.mapIndexed { index, c ->
                val vector = PAD_VECTOR.clone()
                vector[index] = 1
                c to vector
            }.toMap(this)
        }

        private const val DEFAULT_MODEL_ASSET_FILE_PATH = "file:///android_asset/model.tflite"
        private const val DEFAULT_LABEL_ASSET_FILE_NAME = "file:///android_asset/labels.txt"

        private const val INPUT_NAME = "dropout_1_input"
        private const val OUTPUT_NAME = "activation_1/Softmax"
        private const val MAX_RESULTS = 3
        private const val RUN_STATS = true
    }

}