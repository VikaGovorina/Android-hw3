package com.example.hw3vkapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MealAdapter(private val meals: List<Meal>, private val onClickMeal: (String) -> Unit) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    inner class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealName = view.findViewById<TextView>(R.id.mealName)
        val mealImage = view.findViewById<ImageView>(R.id.mealImage)

        init {
            view.setOnClickListener {
                onClickMeal(meals[adapterPosition].idMeal)
            }
        }

        fun bind(meal: Meal) {
            mealName.text = meal.strMeal
            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .into(mealImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false))
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }
}