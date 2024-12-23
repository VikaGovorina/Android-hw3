package com.example.makeupapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.makeupapp.databinding.ProductItemLayoutBinding

class ProductAdapter(private val products:
                     LiveData<List<Product>>, private val onItemClick: (Product?) -> Unit): ListAdapter<Product, ProductAdapter.ProductViewHolder>(comparator)  {
    
    inner class ProductViewHolder(var binding: ProductItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            itemView.setOnClickListener  {
                onItemClick(products.value?.get(adapterPosition))
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
        holder.binding.productName.text = products.value?.get(position)?.name
        holder.binding.productImage.load(products.value?.get(position)?.imageLink) {
            placeholder(R.drawable.default_image)
            error(R.drawable.default_image)
        }

        holder.binding.productPrice.text = "$${products.value?.get(position)?.price}"
        holder.binding.productTags.text = products.value?.get(position)?.tagList?.joinToString(separator = ", ") { it ?: "" }

    }
}