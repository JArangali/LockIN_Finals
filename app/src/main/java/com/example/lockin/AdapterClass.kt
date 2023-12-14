package com.example.lockin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(var dataList : ArrayList<DataClass>) :
    RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {


    private lateinit var rvListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        rvListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView, rvListener)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, Passwords: Int) {
        var currentItems = dataList[Passwords]
        holder.rvUsername.text = currentItems.username
        holder.rvWebsite.text = currentItems.websiteName
        holder.rvPassword.text = currentItems.password
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var rvPassword: TextView = itemView.findViewById(R.id.rvPassword)
        var rvUsername: TextView = itemView.findViewById(R.id.rvUsername)
        var rvWebsite: TextView = itemView.findViewById(R.id.rvWebsite)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}

