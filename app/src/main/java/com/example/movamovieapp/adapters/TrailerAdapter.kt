package com.example.movamovieapp.adapters

import android.util.Log
import com.example.movamovieapp.databinding.ItemTrailerBinding
import com.example.movamovieapp.model.Result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.model.ResultXX
import com.example.movamovieapp.model.detail.DetailResponse
import com.example.movamovieapp.model.video.Video

class TrailerAdapter(): RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {
    private val trailerList = arrayListOf<Video>()

   lateinit var onItemClick: (Video) -> Unit


    class TrailerViewHolder(val itemTrailerBinding: ItemTrailerBinding):
        RecyclerView.ViewHolder(itemTrailerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val item = trailerList[position]
        holder.itemTrailerBinding.trailer = item


        holder.itemTrailerBinding.buttonTest.setOnClickListener {
            onItemClick.invoke(item)
            Log.d("VideoKey",item.toString())
        }
        holder.itemTrailerBinding.cvTrailer.setOnClickListener {
            onItemClick.invoke(item)
            Log.d("VideoKey",item.toString())
        }



//        holder.itemTrailerBinding.cvTrailer.setOnClickListener {data->
//            onItemClick.invoke(item)
//            Log.d("VideoKey",item.toString())
//
//        }


    }

    fun updateList(newList: List<Video>){
        trailerList.clear()
        trailerList.addAll(newList)
        notifyDataSetChanged()
    }
}