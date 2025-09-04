package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemSmallPhotoBinding
import com.example.movamovieapp.model.Result

class FilmsAdapter(
    private val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    val flims = arrayListOf<Result>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmsViewHolder {
val binding = ItemSmallPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilmsViewHolder,
        position: Int
    ) {
        val item = flims[position]
        holder.itemSmallPhotoBinding.mova = item


        holder.itemSmallPhotoBinding.root.setOnClickListener {
            item.id?.let {
                onItemClick(item.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return flims.size

    }

    fun updateList(newList: List<Result>) {
        flims.clear()
        flims.addAll(newList)
        notifyDataSetChanged()
    }

    class FilmsViewHolder(val itemSmallPhotoBinding: ItemSmallPhotoBinding) :
        RecyclerView.ViewHolder(itemSmallPhotoBinding.root)
}