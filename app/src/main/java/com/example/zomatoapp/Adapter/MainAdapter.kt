package com.example.zomatoapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Modals.MainDataModal
import com.example.zomatoapp.R
import com.squareup.picasso.Picasso

class MainAdapter(
    val listener:onClicked,
    val Array:ArrayList<MainDataModal>,


):RecyclerView.Adapter<MainAdapter.myViewHolder>() {

    interface  onClicked{
        fun Clocked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.coustom,parent,false))

    }

    override fun getItemCount(): Int {
        return Array.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val current=Array[position]
        holder.name.text=current.type
        Picasso.get()
            .load(current.image)
            .placeholder(R.drawable.pl)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            listener.Clocked(position)
        }
    }
    class myViewHolder (view:View):RecyclerView.ViewHolder(view){
        val image=view.findViewById<ImageView>(R.id.productImage)
        val name=view.findViewById<TextView>(R.id.productName)

    }
}