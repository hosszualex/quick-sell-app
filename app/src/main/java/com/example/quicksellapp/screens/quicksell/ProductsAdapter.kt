package com.example.quicksellapp.screens.quicksell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quicksellapp.databinding.ListItemProductBinding
import com.example.quicksellapp.model.Product

class ProductsAdapter(private val clickListener: IOnProductClickListener): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var items: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setDataSource(newItems: List<Product>) {
        val diffUtil = ProductsDiffUtil(this.items, newItems)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.items = newItems
        diffResults.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.item = item
            binding.listener = clickListener
            binding.executePendingBindings()
        }
    }

    interface IOnProductClickListener {
        fun onProductClicked(order: Product)
    }
}