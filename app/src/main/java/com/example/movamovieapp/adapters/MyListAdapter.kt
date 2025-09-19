package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemMylistBinding
import com.example.movamovieapp.model.MyListModel

class MyListAdapter: RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {
private val movalist= arrayListOf<MyListModel>()

    lateinit var onItemClickListener: ((MyListModel) -> Unit)

    lateinit var onClick: ((MyListModel) -> Unit)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyListViewHolder {
        val view = ItemMylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyListViewHolder,
        position: Int
    ) {
val item = movalist[position]

        holder.itemMyListBinding.movie = item

        holder.itemMyListBinding.imageViewCheck.setOnClickListener {
            onItemClickListener.invoke(item)
        }
        holder.itemMyListBinding.root.setOnClickListener {
            onClick.invoke(item)
        }
    }

    override fun getItemCount(): Int {
return movalist.size
    }fun updateList(newList: List<MyListModel>) {
        movalist.clear()
        movalist.addAll(newList)
        notifyDataSetChanged()
    }

    class MyListViewHolder (val itemMyListBinding: ItemMylistBinding) :RecyclerView.ViewHolder(itemMyListBinding.root)
}