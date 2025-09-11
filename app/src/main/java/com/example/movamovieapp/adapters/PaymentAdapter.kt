package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.ItemPaymentBinding
import com.example.movamovieapp.model.PaymentModel


class PaymentAdapter:RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>(){
    var onItemClickListener: ((PaymentModel) -> Unit)? = null
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

        val selected=item.selected

        if (selected){
            holder.itemPaymentBinding.imageViewbosbuton.setImageResource(R.drawable.selectedpayment)

        }else{
            holder.itemPaymentBinding.imageViewbosbuton.setImageResource(R.drawable.unselectedpayment)
        }
holder.itemPaymentBinding.materialCardViewcards.setOnClickListener {
    onItemClickListener?.invoke(item)
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