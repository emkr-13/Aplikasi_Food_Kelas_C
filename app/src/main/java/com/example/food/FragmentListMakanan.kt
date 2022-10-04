package com.example.food

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.widget.Button
import android.widget.Toast
import com.example.food.entity.Pesanan
import com.example.food.user.Constant
import com.example.food.user.Makanan
import com.example.food.user.UserDB
import kotlinx.android.synthetic.main.fragment_list_makanan.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentListMakanan : Fragment() {

    val db by lazy { activity?.let { UserDB(it) } }
    lateinit var makananAdapater: MakananAdapater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_makanan,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        makananAdapater = MakananAdapater(arrayListOf(), object :
            MakananAdapater.OnAdapterListener{
            override fun onClick(makanan: Makanan) {
//                Toast.makeText(requireContext().applicationContext, makanan.name, Toast.LENGTH_SHORT).show()
//                Toast.makeText(requireContext().applicationContext, makanan.harga, Toast.LENGTH_SHORT).show()
                intentEdit(makanan.id,Constant.TYPE_READ)
            }
            override fun onUpdate(makanan: Makanan) {
                intentEdit(makanan.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(makanan: Makanan) {
                deleteDialog(makanan)
            }
        })
        rv_makanan.apply {
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            adapter = makananAdapater
        }
    }

    private fun deleteDialog(makanan: Makanan){
        val alertDialog = android.app.AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From ${makanan.name}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {

                    db!!.makananDao().deleteMakan(makanan)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun setupListener() {
        btn_add.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db!!.makananDao().getMakan()
            Log.d("MainActivity","dbResponse: $notes")
            withContext(Dispatchers.Main){

                makananAdapater.setData(notes)
            }
        }
    }

    fun intentEdit(noteId : Int, intentType: Int){
        startActivity(
            Intent(requireActivity().applicationContext, MakananEdit::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }


}