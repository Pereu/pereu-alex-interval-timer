package com.pereu.intervaltimer.di

import com.pereu.intervaltimer.data.api.ApiService
import com.pereu.intervaltimer.data.api.createApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = createApiService()
}