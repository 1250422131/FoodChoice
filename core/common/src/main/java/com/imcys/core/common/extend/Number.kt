package com.imcys.core.common.extend

/**
 * 数字格式化
 * 10000 -> 1万
 */
fun Long.digitalConversion(): String {
    val num = this.toString()
    return if (this >= 10000) {
        val count = num.length
        val unit = when {
            count <= 4 -> ""
            count <= 8 -> "万"
            count <= 12 -> "亿"
            count <= 16 -> "万亿"
            else -> "亿亿"
        }
        val formattedNum = when {
            count <= 8 -> "${num.substring(0, count - 4)}.${num.substring(count - 4, count - 3)}"
            count <= 12 -> "${num.substring(0, count - 8)}.${num.substring(count - 8, count - 7)}"
            count <= 16 -> "${num.substring(0, count - 12)}.${num.substring(count - 12, count - 11)}"
            else -> "${num.substring(0, count - 16)}.${num.substring(count - 16, count - 15)}"
        }
        "$formattedNum$unit"
    } else {
        num
    }
}
