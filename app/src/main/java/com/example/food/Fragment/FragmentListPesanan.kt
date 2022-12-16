package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.*
import com.example.food.databinding.FragmentListPesananBinding
import com.example.food.entity.Pesanan



class FragmentListPesanan : Fragment() {
    private var _binding: FragmentListPesananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_pesanan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        val btnmkn=binding.btnmkn
        val btnpsn=binding.btnpsn
        val btnkmtr=binding.btnkomentar

        btnmkn.setOnClickListener(){
            val intent = Intent(context, MakananList::class.java)
            startActivity(intent)
        }






    }


}
