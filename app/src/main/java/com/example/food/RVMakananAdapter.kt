package com.example.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.entity.Makanan

class RVMakananAdapter (private  val data: Array<Makanan>) : RecyclerView.Adapter<RVMakananAdapter.viewHolder>(){

    class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val tvMakanan: TextView = itemView.findViewById(R.id.tv_makanan)
        val tvDetailsMakanan: TextView = itemView.findViewById(R.id.tv_details)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_makanan,parent,false)
        return RVMakananAdapter.viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvMakanan.text=currentItem.name
        holder.tvDetailsMakanan.text=currentItem.harga
    }

    override fun getItemCount(): Int {
        return data.size
    }
}