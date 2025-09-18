package com.example.movamovieapp.screen.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemContactBinding

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    val contactList = arrayListOf<Contact>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
val item =contactList[position]
        holder.itemContactBinding.contact=item
        holder.itemContactBinding.imageViewcontact.setImageResource(item.icon)
    }

    override fun getItemCount(): Int {
return contactList.size
    } fun updateList(list:List<Contact>){
        contactList.clear()
        contactList.addAll(list)
        notifyDataSetChanged()
    }

    class ContactViewHolder(val itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
    }
}