package com.example.note.util

import android.content.res.Resources
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun EditText.setHeightByLines(padding: Int = 10) {
    layoutParams = layoutParams.also {
        it.height = layout.getLineTop(lineCount) + dpToPx(padding)
    }
}

fun View.showToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun timeToString(time: LocalDateTime, patternStr: String = "yyyy-MM-dd HH:mm:ss"): String {
    val pattern = DateTimeFormatter.ofPattern(patternStr)
    return time.format(pattern)
}

fun stringToTime(timeStr: String, patternStr: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime {
    val pattern = DateTimeFormatter.ofPattern(patternStr)
    return LocalDateTime.parse(timeStr, pattern)
}

fun durationString(scheduledTime: LocalDateTime, nowTime: LocalDateTime = LocalDateTime.now()) = Duration.between(nowTime, scheduledTime).run {
    "${toDays()}天${toHours() % 24}时${toMinutes() % 60}分"
}

fun durationString(scheduledStr: String, nowTime: LocalDateTime = LocalDateTime.now()) = Duration.between(nowTime, stringToTime(scheduledStr)).run {
    "${toDays()}天${toHours() % 24}时${toMinutes() % 60}分"
}
fun nowTime() = LocalDateTime.now()

// 时间是否过期
fun isPastTime(scheduledStr: String, nowTime: LocalDateTime = LocalDateTime.now()) = Duration.between(nowTime, stringToTime(scheduledStr)).run {
    toMillis() > 0
}

// timeStr1在timeStr2之前返回true
fun isBeforeTime(timeStr1: String, timeStr2: String) = Duration.between(stringToTime(timeStr1), stringToTime(timeStr2)).run {
    toMillis().toInt()
}
fun main() {
    val now = LocalDateTime.now()
    val time = stringToTime("2023-05-24 22:33:22")
    println(durationString(time!!, now))
}