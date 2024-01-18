package com.example.rocketproject.di

import com.example.rocketproject.data.RocketService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        val contentType = MediaType.get("application/json")

        return Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    fun providesRocketService(retrofit: Retrofit): RocketService {
        return retrofit.create(RocketService::class.java)
    }
}
