package com.example.food

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.user.Makanan
import kotlinx.android.synthetic.main.activity_makanan_adapater.view.*

class MakananAdapater (private val notes: ArrayList<Makanan>, private val listener: OnAdapterListener) :


    RecyclerView.Adapter<MakananAdapater.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.activity_makanan_adapater,parent, false)
        )
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]


        holder.view.text_title.text=note.name
        holder.view.text_harga.text=note.harga

        holder.view.text_title.setOnClickListener{
            listener.onClick(note)
        }
        holder.view.text_harga.setOnClickListener{
            listener.onClick(note)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(note)
        }

        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(note)
        }

    }
    override fun getItemCount() = notes.size
    inner class NoteViewHolder( val view: View) : RecyclerView.ViewHolder(view)


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Makanan>){
        notes.clear()
        notes.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(makanan: Makanan)
        fun onUpdate(makanan: Makanan)
        fun onDelete(makanan: Makanan)

    }
}