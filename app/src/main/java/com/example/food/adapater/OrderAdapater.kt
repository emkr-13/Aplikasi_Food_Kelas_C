package com.example.food.adapater

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.R

class OrderAdapater {



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvNama: TextView
        var tvTotal: TextView


        var btnDelete: ImageButton
        var cvPesanan: CardView

        init{
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvTotal = itemView.findViewById(R.id.tv_total)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvPesanan = itemView.findViewById(R.id.cv_pesanan)
        }
    }
}