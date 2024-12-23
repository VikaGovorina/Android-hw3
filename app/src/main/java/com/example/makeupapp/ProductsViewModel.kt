package com.example.makeupapp

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    @OptIn(DelicateCoroutinesApi::class)
    fun loadProducts() {
        if (_products.value != null) return
        GlobalScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            delay(2000)
            RetrofitClient.service.getAllProducts().enqueue(object : Callback<List<Product>> {
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = t.message
                }
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        if (response.code() == 200) {
                            val filteredProducts = response.body()!!.filter { product ->
                                val isValidPrice = product.price?.toDoubleOrNull()?.let { it > 0.0 } ?: false
                                isValidPrice
                            }
                            _products.value = filteredProducts
                        }
                    } else {
                        _errorMessage.value = "Error while fetching data"
                    }
                }
            })
        }
    }

}