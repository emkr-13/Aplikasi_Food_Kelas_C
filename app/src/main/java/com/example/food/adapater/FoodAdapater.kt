package com.example.food.adapater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.AddEditMakanan
import com.example.food.MakananList
import com.example.food.R

import com.example.food.model.food
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class FoodAdapater (private  var foodList: List<food>, context: Context)
    :RecyclerView.Adapter<FoodAdapater.ViewHolder>(),
Filterable{
    private var filteredFoodList: MutableList<food>
    private val context: Context

    init{
        filteredFoodList = ArrayList(foodList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_makanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount():Int{
        return filteredFoodList.size
    }

    fun setMakanananList(mahasiswaList: Array<food>){
        this.foodList = mahasiswaList.toList()
        filteredFoodList = mahasiswaList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val makanan = filteredFoodList[position]
        holder.tvNama.text=makanan.name
        holder.tvHarga.text=makanan.harga

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data makanan ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is MakananList) makanan.id?.let { it1 ->
                        context.deleteMakanan(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvMakanan.setOnClickListener{
            val i = Intent(context, AddEditMakanan::class.java)
            i.putExtra("id", makanan.id)
            if(context is MakananList)
                context.startActivityForResult(i, MakananList.LAUNCH_ADD_ACTIVITY)
        }



    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<food> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(foodList)
                }else{
                    for(mahasiswa in foodList){
                        if(mahasiswa.name.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(mahasiswa)
                    }
                }
                val filterResult = FilterResults()
                filterResult.values = filtered
                return filterResult
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredFoodList.clear()
                filteredFoodList.addAll((filterResults.values as List<food>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var tvNama: TextView
        var tvHarga: TextView


        var btnDelete: ImageButton
        var cvMakanan: CardView

        init{
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvHarga = itemView.findViewById(R.id.tv_harga)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvMakanan = itemView.findViewById(R.id.cv_makanan)
        }
    }
}