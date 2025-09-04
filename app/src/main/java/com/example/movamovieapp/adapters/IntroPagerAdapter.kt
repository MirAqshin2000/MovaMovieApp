package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemPagerBinding
import com.example.movamovieapp.model.IntroModel


class IntroPagerAdapter : RecyclerView.Adapter<IntroPagerAdapter.IntroPagerViewHolder>() {

    private val introPagerList = arrayListOf<IntroModel>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroPagerViewHolder {
        val view= ItemPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return IntroPagerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: IntroPagerViewHolder,
        position: Int
    ) {

        val item = introPagerList[position]
        holder.itempagerBinding.intro = item


        holder.itempagerBinding.executePendingBindings()

    }
    override fun getItemCount(): Int {
return introPagerList.size
    }
    fun updateList(newList: List<IntroModel>){
        introPagerList.clear()
        introPagerList.addAll(newList)
        notifyDataSetChanged()
    }

    class IntroPagerViewHolder(val itempagerBinding: ItemPagerBinding):  RecyclerView.ViewHolder(itempagerBinding.root)


}
