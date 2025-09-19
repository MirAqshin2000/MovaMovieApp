package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.ItemCardsBinding
import com.example.movamovieapp.databinding.ItemPaymentBinding
import com.example.movamovieapp.model.CardModel
import com.example.movamovieapp.util.maskCardNumberGrouped

class CardAdapter:RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private val cardList = arrayListOf<CardModel>()
    private var selectedPosition = -1

    fun clearSelection() {
        selectedPosition = -1
        notifyDataSetChanged()
    }

    var onItemClickListener: ((CardModel) -> Unit)? = null

    var onSelectClickListener: ((CardModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view= ItemCardsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
val item=cardList[position]
//        holder.itemCardsBinding.textViewbank.text=maskCardNumberGrouped(item.cardNumber)
//        holder.itemCardsBinding.imageViewcards.setImageResource(item.cardImage)


        holder.itemCardsBinding.textViewbank.text=item.cardName
        when (item.cardName) {
            "PayPal" -> {
                holder.itemCardsBinding.textViewbank.text = "PayPal"
                holder.itemCardsBinding.imageViewcards.setImageResource(R.drawable.paypallogo)
            }
            "Google Pay" -> {
                holder.itemCardsBinding.textViewbank.text = "Google Pay"
                holder.itemCardsBinding.imageViewcards.setImageResource(R.drawable.google)
        }
            "Apple Pay" -> {
                holder.itemCardsBinding.textViewbank.text = "Apple Pay"

                val isNightMode=(holder.itemView.context.resources.configuration.uiMode
                        and android.content.res.Configuration.UI_MODE_NIGHT_MASK)==android.content.res.Configuration.UI_MODE_NIGHT_YES
                if (isNightMode)
                    holder.itemCardsBinding.imageViewcards.setImageResource(R.drawable.apple)
                else{
                    holder.itemCardsBinding.imageViewcards.setImageResource(R.drawable.appleblack)
                }
            }
            else -> {

                holder.itemCardsBinding.textViewbank.text =maskCardNumberGrouped(item.cardNumber)
                holder.itemCardsBinding.imageViewcards.setImageResource(R.drawable.mastercard)
            }

        }



holder.itemCardsBinding.imageViewcheckh.visibility=
    if (selectedPosition == position) View.VISIBLE
    else View.GONE

        holder.itemCardsBinding.imageViewcheckh.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        holder.itemCardsBinding.materialCardViewcards.setOnClickListener {
            selectedPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()

            onSelectClickListener?.invoke(item)
        }

    }

    override fun getItemCount(): Int {
        return cardList.size
    }
    fun updateList(list:List<CardModel>){
        cardList.clear()
        cardList.addAll(list)
        notifyDataSetChanged()

    }

    class CardViewHolder(val itemCardsBinding: ItemCardsBinding) : RecyclerView.ViewHolder(itemCardsBinding.root)
}