package de.markusressel.kodehighlighter.language_detection

/**
 * An immutable result returned by a Classifier describing what was recognized.
 */
data class RecognitionResult(
        /**
         * A unique identifier for what has been recognized. Specific to the class, not the instance of
         * the object.
         */
        val id: Int,
        /**
         * Display name for the recognition.
         */
        val title: String,
        /**
         * A sortable score for how good the recognition is relative to others. Higher should be better.
         */
        val confidence: Float)