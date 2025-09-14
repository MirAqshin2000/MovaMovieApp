package com.example.movamovieapp.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemFaqBinding

class FaqAdapter :RecyclerView.Adapter<FaqAdapter.FaqViewHolder>(){

    val faqList = arrayListOf<Faq>()

    lateinit var onClick: (Faq) -> Unit
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FaqViewHolder {
val view=ItemFaqBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FaqViewHolder,
        position: Int
    ) {
val item =faqList[position]
        holder.itemFaqBinding.faq=item
holder.itemFaqBinding.textViewanswer.visibility=
    if (item.isExpanded) View.VISIBLE  else View.GONE

        holder.itemFaqBinding.imageViewdropdown.setOnClickListener {
            item.isExpanded = !item.isExpanded
            holder.itemFaqBinding.textViewanswer.visibility=
                if (item.isExpanded) View.VISIBLE else View.GONE



            onClick.invoke(item)
        }
    }

    override fun getItemCount(): Int {
return faqList.size
    }
    fun updateList(list:List<Faq>){
        faqList.clear()
        faqList.addAll(list)
        notifyDataSetChanged()

    }

    class FaqViewHolder( val itemFaqBinding: ItemFaqBinding) : RecyclerView.ViewHolder(itemFaqBinding.root){

    }
}