package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.ItemPaymentBinding
import com.example.movamovieapp.model.PaymentModel


class PaymentAdapter:RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>(){
    private var selectedPosition = -1
    fun clearSelection() {
        selectedPosition = -1
        notifyDataSetChanged()
    }
    var onItemClickListener: ((PaymentModel) -> Unit)? = null
    var onSelectClickListener: ((PaymentModel) -> Unit)? = null
     val paymentlist= arrayListOf<PaymentModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentViewHolder {
        val view=ItemPaymentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: PaymentViewHolder,
        position: Int
    ) {
val item=paymentlist[position]
        holder.itemPaymentBinding.payment=item

        holder.itemPaymentBinding.imageView67.setImageResource(item.image)

        holder.itemPaymentBinding.imageView56.visibility=
            if (selectedPosition == position) View.VISIBLE
            else View.GONE

        holder.itemPaymentBinding.materialCardViewcards.setOnClickListener {
            selectedPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()
            onSelectClickListener?.invoke(item)
        }



    }

    override fun getItemCount(): Int {
return paymentlist.size
    }fun updateList(list:List<PaymentModel>){
        paymentlist.clear()
        paymentlist.addAll(list)
        notifyDataSetChanged()

    }

    class PaymentViewHolder(val itemPaymentBinding: ItemPaymentBinding) :RecyclerView.ViewHolder(itemPaymentBinding.root)

}