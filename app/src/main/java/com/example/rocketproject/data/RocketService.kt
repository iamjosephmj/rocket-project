package com.example.rocketproject.data

import com.example.rocketproject.data.model.SpaceflightNewsResponse
import retrofit2.http.GET


internal interface RocketService {

    @GET("v4/articles/")
    suspend fun fetchFlights(): SpaceflightNewsResponse
}
