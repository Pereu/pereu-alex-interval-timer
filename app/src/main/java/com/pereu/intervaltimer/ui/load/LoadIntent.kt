package com.pereu.intervaltimer.ui.load

sealed interface LoadIntent {
    @JvmInline value class IdChanged(val id: String) : LoadIntent
    object LoadClicked : LoadIntent
}