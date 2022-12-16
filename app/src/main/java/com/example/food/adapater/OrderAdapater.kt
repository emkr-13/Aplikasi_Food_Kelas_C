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
import com.example.food.AddEditPesanan


import com.example.food.PesananList
import com.example.food.R

import com.example.food.model.order
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapater  (private  var orderList: List<order>, context: Context)
    :RecyclerView.Adapter<OrderAdapater.ViewHolder>(),
    Filterable {

    private var filteredOrderList: MutableList<order>
    private val context: Context

    init{
        filteredOrderList = ArrayList(orderList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): OrderAdapater.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount():Int{
        return filteredOrderList.size
    }

    fun setPesananList(mahasiswaList: Array<order>){
        this.orderList = mahasiswaList.toList()
        filteredOrderList = mahasiswaList.toMutableList()
    }

    override fun onBindViewHolder(holder: OrderAdapater.ViewHolder, position: Int){
        val pesanan = filteredOrderList[position]
        holder.tvNama.text=pesanan.nama
        holder.tvTotal.text= pesanan.total_pesanan

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data makanan ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is PesananList) pesanan.id?.let { it1 ->
                        context.deletePesanan(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvPesanan.setOnClickListener{
            val i = Intent(context, AddEditPesanan::class.java)
            i.putExtra("id", pesanan.id)
            if(context is PesananList)
                context.startActivityForResult(i, PesananList.LAUNCH_ADD_ACTIVITY)
        }



    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<order> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(orderList)
                }else{
                    for(mahasiswa in orderList){
                        if(mahasiswa.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(mahasiswa)
                    }
                }
                val filterResult = FilterResults()
                filterResult.values = filtered
                return filterResult
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredOrderList.clear()
                filteredOrderList.addAll((filterResults.values as List<order>))
                notifyDataSetChanged()
            }
        }
    }


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