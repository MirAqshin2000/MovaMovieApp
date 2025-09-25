package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.adapters.MoreLikeAdapter.MoreViewHolder
import com.example.movamovieapp.databinding.ItemMoreBinding
import com.example.movamovieapp.model.Result
class MoreLikeAdapter : ListAdapter<Result, MoreLikeAdapter.MoreViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: ((Result) -> Unit)? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem
        }
    }

    inner class MoreViewHolder(val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.more = item
            binding.cvMore.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder {
        val binding = ItemMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateMore(list: List<Result>) {
        submitList(list)
    }
}