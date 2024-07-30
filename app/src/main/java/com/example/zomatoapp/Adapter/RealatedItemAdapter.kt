package com.example.zomatoapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.MainAdapter.myViewHolder
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.squareup.picasso.Picasso

class RealatedItemAdapter(
    val lstner:onClickedListener,
    val Array:ArrayList<RealatedtemDataModal>
):RecyclerView.Adapter<RealatedItemAdapter.myViewHolder>() {

    interface onClickedListener{
        fun cliked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.realated_row, parent, false))


    }

    override fun getItemCount(): Int {
        return Array.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val current=Array[position]
        holder.name.text=current.itemName
        holder.price.text=current.itemPrce
        Picasso.get()
            .load(current.image)
            .placeholder(R.drawable.pl)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            lstner.cliked(position)
        }
    }

    class myViewHolder (view:View):RecyclerView.ViewHolder(view){
        val image:ImageView=view.findViewById(R.id.realatedItemImage)
        val name:TextView=view.findViewById(R.id.realatedItemName)
        val price:TextView=view.findViewById(R.id.realatedItemPrice)
    }
}