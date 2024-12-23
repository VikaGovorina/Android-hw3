package com.example.makeupapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MakeupApi {
    @GET("products.json")
    fun getAllProducts(): Call<List<Product>>
}

object RetrofitClient {
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://makeup-api.herokuapp.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(MakeupApi::class.java)
}

@Parcelize
data class Product(
    @SerializedName("api_featured_image")
    var apiFeaturedImage: String?,
    @SerializedName("brand")
    var brand: String?,
    @SerializedName("category")
    var category: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("currency")
    var currency: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_link")
    var imageLink: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("price")
    var price: String?,
    @SerializedName("price_sign")
    var priceSign: String?,
    @SerializedName("product_api_url")
    var productApiUrl: String?,
    @SerializedName("product_link")
    var productLink: String?,
    @SerializedName("product_type")
    var productType: String?,
    @SerializedName("tag_list")
    var tagList: List<String?>?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("website_link")
    var websiteLink: String?
) : Parcelable
