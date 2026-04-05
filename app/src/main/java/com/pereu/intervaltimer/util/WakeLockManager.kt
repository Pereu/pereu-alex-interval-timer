package com.pereu.intervaltimer.util

import android.content.Context
import android.os.PowerManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WakeLockManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val wakeLock: PowerManager.WakeLock =
        (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "IntervalTimer::TimerWakeLock")

    fun acquire() {
        if (!wakeLock.isHeld) wakeLock.acquire(30 * 60 * 1000L) // 30 минут
    }

    fun release() {
        if (wakeLock.isHeld) wakeLock.release()
    }
}