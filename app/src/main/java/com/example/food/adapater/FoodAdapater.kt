package com.example.food.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.food.MakananList
import com.example.food.R
import com.example.food.model.Makanan

class MakananAdapater (
    private var makananList: List<Makanan>, context: Context
)
    : RecyclerView.Adapter<MakananAdapater.ViewHolder>(),
    Filterable {
    private var filteredMakananList: MutableList<MakananList>
    private val context: Context

    init{
        filteredMakananList = ArrayList(makananList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_makanan, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount():Int{
        return filteredMakananList.size
    }

    fun setMakananList(makananList: Array<Makanan>){
        this.makananList = makananList.toList()
        filteredMakananList = makananList.toMutableList()
    }
}