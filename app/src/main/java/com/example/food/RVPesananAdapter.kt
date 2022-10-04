package com.example.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.entity.Pesanan


class RVPesananAdapter (private  val data: Array<Pesanan>) : RecyclerView.Adapter<RVPesananAdapter.viewHolder>() {

    class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val tvPesanan: TextView = itemView.findViewById(R.id.tv_pesanan)
        val tvDetailsPesanan: TextView = itemView.findViewById(R.id.tv_details_Pesanan)
        val tvGambar : ImageView = itemView.findViewById(R.id.tv_gambar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVPesananAdapter.viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_pesanan,parent,false)
        return RVPesananAdapter.viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RVPesananAdapter.viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvPesanan.text=currentItem.name
        holder.tvDetailsPesanan.text=currentItem.harga
        holder.tvGambar.setImageResource(currentItem.photo)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}