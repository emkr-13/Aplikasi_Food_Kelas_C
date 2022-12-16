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
import com.example.food.AddEditKomentar

import com.example.food.KomentarList

import com.example.food.R
import com.example.food.model.komentar

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList


class KomentarAdapater (private  var komentarList: List<komentar>, context: Context)
    :RecyclerView.Adapter<KomentarAdapater.ViewHolder>(),
    Filterable {

    private var filteredKomentarList: MutableList<komentar>
    private val context: Context

    init{
        filteredKomentarList = ArrayList(komentarList)
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): KomentarAdapater.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_komentar, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount():Int{
        return filteredKomentarList.size
    }

    fun setKomentarList(mahasiswaList: Array<komentar>){
        this.komentarList = mahasiswaList.toList()
        filteredKomentarList = mahasiswaList.toMutableList()
    }

    override fun onBindViewHolder(holder: KomentarAdapater.ViewHolder, position: Int){
        val pesanan = filteredKomentarList[position]
        holder.tvNama.text=pesanan.nama_makanan
        holder.tvJenis.text= pesanan.jenis_komentar

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data makanan ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is KomentarList) pesanan.id?.let { it1 ->

                    }
                }
                .show()
        }
        holder.cvKomentar.setOnClickListener{
            val i = Intent(context, AddEditKomentar::class.java)
            i.putExtra("id", pesanan.id)
            if(context is KomentarList)
                context.startActivityForResult(i, KomentarList.LAUNCH_ADD_ACTIVITY)
        }



    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<komentar> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(komentarList)
                }else{
                    for(mahasiswa in komentarList){
                        if(mahasiswa.jenis_komentar.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(mahasiswa)
                    }
                }
                val filterResult = FilterResults()
                filterResult.values = filtered
                return filterResult
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredKomentarList.clear()
                filteredKomentarList.addAll((filterResults.values as List<komentar>))
                notifyDataSetChanged()
            }
        }
    }




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