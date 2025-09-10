package com.example.movamovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ItemNotificationBinding
import com.example.movamovieapp.model.Result

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    val notifications = arrayListOf<Result>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
val view = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int
    ) {
val item = notifications[position]
        holder.itemnotificationBinding.noti = item
        holder.itemnotificationBinding.executePendingBindings()

    }

    override fun getItemCount(): Int {
return notifications.size
    }fun updateList(newLists: List<Result>){
        notifications.clear()
        notifications.addAll(newLists)
        notifyDataSetChanged()
    }

    class NotificationViewHolder(val itemnotificationBinding: ItemNotificationBinding) : RecyclerView.ViewHolder( itemnotificationBinding.root)
}