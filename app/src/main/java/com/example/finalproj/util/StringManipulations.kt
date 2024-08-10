package com.example.finalproj.util

import android.annotation.SuppressLint
import kotlin.math.roundToInt

fun capitalizeName(name: String): String {
    return name.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}


@SuppressLint("DefaultLocale")
fun roundToHalf(value: Any): Any {
    val floatValue = when (value) {
        is Number -> value.toFloat()
        else -> throw IllegalArgumentException("Input must be a Number or Float")
    }
    val roundedValue = (floatValue * 12).roundToInt() / 12.0
    return if (roundedValue % 1.0 == 0.0) {
        roundedValue.toInt()
    } else {
        String.format("%.2f", roundedValue).toFloat()
    }
}

