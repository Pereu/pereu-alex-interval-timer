package com.pereu.intervaltimer.util

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class TTSManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private var tts: TextToSpeech? = null
    private var isReady = false

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("ru")
                isReady = true
            }
        }
    }

    fun speak(text: String) {
        if (!isReady) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
    }
}