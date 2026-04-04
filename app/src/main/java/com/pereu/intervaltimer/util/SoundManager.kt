package com.pereu.intervaltimer.util

import android.media.AudioManager
import android.media.ToneGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor() {

    private val toneGenerator = ToneGenerator(
        AudioManager.STREAM_MUSIC,
        ToneGenerator.MAX_VOLUME
    )

    fun playStart() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500)
    }

    fun playIntervalChange() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 300)
    }

    fun playFinish() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 750)
    }

    fun release() {
        toneGenerator.release()
    }
}