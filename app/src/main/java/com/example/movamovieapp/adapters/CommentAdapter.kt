package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemCommentBinding
import com.example.movamovieapp.model.ResultX

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    val list = arrayListOf<ResultX>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CommentViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.itemcommentBinding.comment = item
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<ResultX>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class CommentViewHolder(val itemcommentBinding: ItemCommentBinding) :
        RecyclerView.ViewHolder(itemcommentBinding.root)

}