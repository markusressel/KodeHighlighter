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
                         val modelFilename: String = DEFAULT_MODEL_ASSET_FILE_NAME,
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
        return DEFAULT_MODEL_ASSET_FILE_NAME
    }

    /**
     * Recognize the language of the given snippet
     *
     * @param snippet the code snipped to analyze
     * @return a list of [Recognition] objects
     */
    fun recognizeLanguage(snippet: String, confidenceThreshold: Float = Float.MIN_VALUE): List<Recognition> {
        val input: ByteArray = snippetToVector(snippet)
        val byteBuffer = ByteBuffer.wrap(input)
        val inferenceResults: FloatArray = runInference(byteBuffer)

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
    private fun snippetToVector(text: String, vectorSize: Int = 8 * 1024, normalizeWhitespace: Boolean = true): ByteArray {
        var inputText = text

        // Normalising whitespace
        // NOTE: this could backfire due to whitespace significant languages
        // but allows for more code consumed
        if (normalizeWhitespace) {
            inputText = inputText.map {
                when (it) {
                    '\n', '\r', '\t' -> ' '
                    else -> it
                }
            }.joinToString(separator = "")
        }

        // reduce multiple whitespaces to a single one to limit their impact
        inputText = inputText.replace("\\s+".toRegex(), " ")

        // map every character (up to the vector size)
        val resultVectorList: MutableList<Array<Byte>> = inputText.take(vectorSize).map {
            CHARACTER_TO_VECTOR_MAP[it]
        }.filterNotNull().toMutableList()

        if (resultVectorList.size < vectorSize) {
            for (j in 0 until (vectorSize - resultVectorList.size)) {
                resultVectorList.add(PAD_VECTOR)
            }
        }

        return resultVectorList.flatMap { it.toList() }.toByteArray()
    }

    private fun runInference(input: ByteBuffer): FloatArray {
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

        /** Mapps supported charactors to their respective vector (used to improve performance) */
        private val CHARACTER_TO_VECTOR_MAP: MutableMap<Char, Array<Byte>> = mutableMapOf<Char, Array<Byte>>().apply {
            SUPPORTED_CHARACTERS.mapIndexed { index, c ->
                val vector = PAD_VECTOR.clone()
                vector[index] = 1
                c to vector
            }.toMap(this)
        }

        private const val DEFAULT_MODEL_ASSET_FILE_NAME = "model.tflite"
        private const val DEFAULT_LABEL_ASSET_FILE_NAME = "labels.txt"

        private const val INPUT_NAME = "dropout_1_input"
        private const val OUTPUT_NAME = "activation_1/Softmax"
        private const val MAX_RESULTS = 3
        private const val RUN_STATS = true
    }

}