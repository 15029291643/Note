package com.example.note.util

import android.content.res.Resources
import android.widget.EditText

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun EditText.setHeightByLines(padding: Int = 10) {
    layoutParams = layoutParams.also {
        if (lineCount == 1) {
            setText(text.toString() + "\n")
        }
        it.height = layout.getLineTop(lineCount) + dpToPx(padding)
    }
}