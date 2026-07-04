package com.jiese.app

import kotlin.math.pow
import kotlin.math.roundToInt

object FormatUtils {
    fun doubleToString(value: Double, decimals: Int): String {
        val factor = 10.0.pow(decimals)
        val rounded = (value * factor).roundToInt()
        val intPart = rounded / factor.toInt()
        val decPart = (rounded % factor.toInt()).toString().padStart(decimals, '0')
        return if (decimals == 0) "$intPart" else "$intPart.$decPart"
    }

    fun floatToString(value: Float, decimals: Int): String {
        return doubleToString(value.toDouble(), decimals)
    }
}

fun Double.format(decimals: Int): String = FormatUtils.doubleToString(this, decimals)

fun Float.format(decimals: Int): String = FormatUtils.floatToString(this, decimals)
