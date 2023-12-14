package com.example.lockin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(var dataList : ArrayList<DataClass>) : RecyclerView.Adapter<AdapterClass.ViewHolderClass>(){



    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rvPassword: TextView = itemView.findViewById(R.id.recyclerPass)
        var rvUsername: TextView = itemView.findViewById(R.id.recyclerUser)
        var rvWebsite: TextView = itemView.findViewById(R.id.recyclerWebsite)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        var currentItems = dataList[position]
        holder.rvUsername.text = currentItems.Username
        holder.rvWebsite.text = currentItems.WebsiteName
        holder.rvPassword.text = currentItems.Password
    }


    override fun getItemCount(): Int {
        return dataList.size
    }
}

