package com.example.hw3vkapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MealResponse>

    @GET("categories.php")
    suspend fun getFoodCategories(): Call<MealResponse>

    @GET("lookup.php")
    fun getMealById(@Query("i") id: String): Call<MealResponse>
}

data class MealResponse(
    val meals: List<Meal>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String
)

object ApiClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val apiService: MealApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(MealApi::class.java)
    }
}