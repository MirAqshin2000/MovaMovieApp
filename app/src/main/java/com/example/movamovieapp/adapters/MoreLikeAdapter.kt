package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.adapters.MoreLikeAdapter.MoreViewHolder
import com.example.movamovieapp.databinding.ItemMoreBinding
import com.example.movamovieapp.model.Result

class MoreLikeAdapter() : RecyclerView.Adapter<MoreViewHolder>() {
lateinit var onItemClickListener: ((Result) -> Unit)
    val morelist = arrayListOf<Result>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreViewHolder {
        val view = ItemMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: MoreViewHolder,
        position: Int
    ) {
        val item = morelist[position]
        holder.itemMoreBinding.more = item

        holder.itemMoreBinding.cvMore.setOnClickListener {
            onItemClickListener.invoke(item)
        }



    }

    override fun getItemCount(): Int {
return morelist.size
    }
    fun updateMore(newList: List<Result>){
        morelist.clear()
        morelist.addAll(newList )
        notifyDataSetChanged()
    }

    class MoreViewHolder(val itemMoreBinding: ItemMoreBinding) :RecyclerView.ViewHolder(itemMoreBinding.root)

}