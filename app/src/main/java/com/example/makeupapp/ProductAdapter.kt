package com.example.makeupapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import androidx.navigation.NavController
import com.example.makeupapp.databinding.ProductItemLayoutBinding

class ProductAdapter(private val products: List<Product>, private val onItemClick: (Product) -> Unit): ListAdapter<Product, ProductAdapter.ProductViewHolder>(comparator)  {
    
    inner class ProductViewHolder(var binding: ProductItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            itemView.setOnClickListener  {
//                println(products[adapterPosition].id)
//                println(products[adapterPosition].name)
//                println(products[adapterPosition].imageLink)
                onItemClick(products[adapterPosition])
            }
        }
    }

    companion object {
        var comparator = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.productName.text = products[position].name
        holder.binding.productImage.load(products[position].imageLink)
        holder.binding.productPrice.text = "$${products[position].price}"
        holder.binding.productTags.text = products[position].tagList?.joinToString(separator = ", ") { it ?: "" }

//        getItem(position).let {
//            holder.binding.productName.text = it.name
//            holder.binding.productImage.load(it.imageLink)
//            holder.binding.productPrice.text = "$${it.price}"
//            holder.binding.productTags.text = it.tagList?.joinToString(separator = ", ") { it ?: "" }
//
//        }
    }
}