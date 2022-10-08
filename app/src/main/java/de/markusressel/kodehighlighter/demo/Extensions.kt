package de.markusressel.kodehighlighter.demo

import android.content.res.Resources
import androidx.annotation.RawRes

fun Resources.readResourceFileAsText(@RawRes resourceId: Int) =
    openRawResource(resourceId).bufferedReader().readText()