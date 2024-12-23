package com.example.hw3vkapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private lateinit var progressBar: ProgressBar
    private val meals = mutableListOf<Meal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meals, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMeals)
        recyclerView.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.progressBar)
        mealAdapter = MealAdapter(meals) { mealId ->
            val detailFragment = MealDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("MEAL_ID", mealId)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = mealAdapter

        loadMeals()

        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadMeals() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(2000)
            ApiClient.apiService.getMealsByCategory("Seafood").enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        meals.clear()
                        meals.addAll(response.body()!!.meals)
                        mealAdapter.notifyItemRangeChanged(0, meals.size)
                    } else {
                        Toast.makeText(context, "Error while fetching data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Network error, try to check internet connection", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}