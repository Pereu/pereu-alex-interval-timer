package com.pereu.intervaltimer.util

import android.media.AudioManager
import android.media.ToneGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor() {

    private var toneGenerator: ToneGenerator? = createToneGenerator()

    private fun createToneGenerator() = try {
        ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
    } catch (_: RuntimeException) {
        null
    }

    private fun play(tone: Int, durationMs: Int) {
        try {
            if (toneGenerator == null) {
                toneGenerator = createToneGenerator()
            }
            toneGenerator?.startTone(tone, durationMs)
        } catch (_: RuntimeException) {
            toneGenerator = createToneGenerator()
            toneGenerator?.startTone(tone, durationMs)
        }
    }

    fun playStart() = play(ToneGenerator.TONE_CDMA_ABBR_ALERT, 3000)

    fun playIntervalChange() = play(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 300)

    fun playFinish() = play(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200)

    fun release() {
        toneGenerator?.release()
        toneGenerator = null
    }
}