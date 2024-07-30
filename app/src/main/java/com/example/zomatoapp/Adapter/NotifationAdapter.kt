package com.example.zomatoapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Modals.NotDataModal
import com.example.zomatoapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotifationAdapter(val Array:ArrayList<NotDataModal>):RecyclerView.Adapter<NotifationAdapter.myViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.noti_coustom,parent,false))
    }

    override fun getItemCount(): Int {
        return Array.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val pos=Array[position]
        holder.icon.setImageResource(R.drawable.order)
        holder.text.text=pos.Order
        holder.title.text=pos.Title
    }
    class myViewHolder (view: View):RecyclerView.ViewHolder(view){
        val icon=view.findViewById<ImageView>(R.id.notiIcon)
        val title=view.findViewById<TextView>(R.id.notiOrder)
        val text=view.findViewById<TextView>(R.id.notiText)
    }
}