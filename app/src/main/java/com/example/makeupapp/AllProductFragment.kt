package com.example.makeupapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.makeupapp.databinding.FragmentAllProductBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllProductFragment : Fragment() {
    lateinit var binding: FragmentAllProductBinding
    lateinit var adapter: ProductAdapter
    private lateinit var progressBar: ProgressBar
    private val products = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllProductBinding.inflate(inflater,container,false)
        progressBar = binding.progressBar

        adapter = ProductAdapter(products) { product: Product ->
            val detailFragment = ProductDetailFragment().apply {
                println(product.name)
                println(product.imageLink)
                arguments = Bundle().apply {
                    putParcelable("product", product)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        loadProducts()

        println("cyka")

        binding.Recyclerviewid.adapter = adapter

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadProducts() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(2000)
            RetrofitClient.service.getAllProducts().enqueue(object : Callback<List<Product>> {
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Toast.makeText(context, "Error while fetching data", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        if (response.code() == 200) {
                            products.clear()
                            products.addAll(response.body()!!)
                            adapter.submitList(response.body())
                        }
                    }

//                    if (response.isSuccessful && response.body() != null) {
//                        products.clear()
//                        products.addAll(response.body()!!.meals)
//                        adapter.notifyItemRangeChanged(0, meals.size)
//                    } else {
//                        Toast.makeText(context, "Error while fetching data", Toast.LENGTH_SHORT).show()
//                    }
                }
            })
        }
    }

}