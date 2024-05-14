package com.andmar.todo

import android.content.Context
import android.os.Vibrator
import android.os.VibrationEffect
import android.os.VibratorManager

class VibratorService(private val context: Context) {

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun vibrate() {
        if(vibrator.hasVibrator()) {
            val vibro = VibrationEffect.createOneShot(50, 50)
            vibrator.vibrate(vibro)
        }
    }
}
