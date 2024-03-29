package de.markusressel.kodehighlighter.core.ui

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color


/**
 * Represents the colors of the input text (without highlighting), background and content used
 * in a code text field in different states.
 *
 * See [KodeTextFieldDefaults.textFieldColors] for the default colors used in [KodeTextFieldDefaults].
 */
@Stable
interface KodeTextFieldColors {
    /**
     * Represents the color used for the input text of this text field.
     *
     * @param enabled whether the text field is enabled
     */
    @Composable
    fun textColor(enabled: Boolean): State<Color>

    /**
     * Represents the color used for the cursor of this text field.
     */
    @Composable
    fun cursorColor(): State<Color>
}


/**
 * Contains the default values used by [KodeTextField].
 */
@Immutable
object KodeTextFieldDefaults {

    /**
     * Creates a [KodeTextFieldColors] that represents the default input text, background
     * and content colors used in a [KodeTextField].
     */
    @Composable
    fun textFieldColors(
        textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),
        disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
        cursorColor: Color = MaterialTheme.colors.primary,
    ): KodeTextFieldColors =
        DefaultKodeTextFieldColors(
            textColor = textColor,
            disabledTextColor = disabledTextColor,
            cursorColor = cursorColor,
        )
}


private data class DefaultKodeTextFieldColors(
    private val textColor: Color,
    private val disabledTextColor: Color,
    private val cursorColor: Color,
) : KodeTextFieldColors {

    @Composable
    override fun textColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) textColor else disabledTextColor)
    }

    @Composable
    override fun cursorColor(): State<Color> {
        return rememberUpdatedState(cursorColor)
    }
}
