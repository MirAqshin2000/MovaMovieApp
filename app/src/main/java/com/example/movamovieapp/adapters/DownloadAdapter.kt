package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemDownloadBinding
import com.example.movamovieapp.model.DownloadModel

class DownloadAdapter:RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {


     var onItemClickListener: ((DownloadModel) -> Unit)?=null
    val downloadList = arrayListOf<DownloadModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DownloadViewHolder {
        val view = ItemDownloadBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DownloadViewHolder,
        position: Int
    ) {
val item = downloadList[position]
        holder.itemDownloadBinding.download = item
        holder.itemDownloadBinding.imageView68delete.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
return downloadList.size
    }fun updateList(newList: List<DownloadModel>) {
        downloadList.clear()
        downloadList.addAll(newList)
        notifyDataSetChanged()
    }

    class DownloadViewHolder(val itemDownloadBinding: ItemDownloadBinding):RecyclerView.ViewHolder(itemDownloadBinding.root)
}