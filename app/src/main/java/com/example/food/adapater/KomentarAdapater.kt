package com.example.food.adapater

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.R

class KomentarAdapater {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvJenis: TextView
        var tvNama: TextView


        var btnDelete: ImageButton
        var cvKomentar: CardView

        init{
            tvJenis = itemView.findViewById(R.id.tv_jenis)
            tvNama = itemView.findViewById(R.id.tv_nama)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvKomentar = itemView.findViewById(R.id.cv_komentar)
        }
    }
}