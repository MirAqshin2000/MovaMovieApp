package com.example.movamovieapp.adapters

import com.example.movamovieapp.model.detail.credits.Cast



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemActorsBinding


class CreditsAdapter : RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder>(){
    val credits = arrayListOf<Cast>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreditsViewHolder {
 val view=ItemActorsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CreditsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CreditsViewHolder,
        position: Int
    ) {
        val item = credits[position]
        holder.itemActorsBinding.actor = item
    }

    override fun getItemCount(): Int {
        return credits.size
    }

    fun updateCredits(newCredits: List<Cast>){
        credits.clear()
        credits.addAll(newCredits)
        notifyDataSetChanged()
    }

    class CreditsViewHolder(val itemActorsBinding: ItemActorsBinding) :
        RecyclerView.ViewHolder(itemActorsBinding.root)
}