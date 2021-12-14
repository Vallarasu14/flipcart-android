package com.example.flipcart.api

import com.example.flipcart.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {

    @get:GET("products")
    val posts : Call<List<PostModel?>?>?

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}