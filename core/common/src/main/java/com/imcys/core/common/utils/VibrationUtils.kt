package com.imcys.core.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.core.content.ContextCompat.getSystemService


object VibrationUtils {
    /**
     * 实现长按触感反馈
     * @param context Context
     */
    @SuppressLint("NewApi")
    fun performHapticFeedback(context: Context){
        val window = (context as? Activity)?.window
        val decorView = window?.decorView
        decorView?.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
    }
}