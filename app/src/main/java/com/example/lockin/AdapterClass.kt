package com.example.lockin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(private val dataList : ArrayList<DataClass>) :
    RecyclerView.Adapter<AdapterClass.ViewHolder>() {


    private lateinit var rvListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        rvListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView, rvListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItems = dataList[position]
        holder.rvUsername.text = currentItems.username
        holder.rvWebsite.text = currentItems.websiteName
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var rvUsername: TextView = itemView.findViewById(R.id.rvUsername)
        var rvWebsite: TextView = itemView.findViewById(R.id.rvWebsite)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}

