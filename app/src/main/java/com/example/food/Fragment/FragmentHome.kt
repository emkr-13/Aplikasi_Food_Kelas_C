package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.CetakPdf
import com.example.food.Location
import com.example.food.RVHomeAdapter
import com.example.food.Scan
import com.example.food.databinding.FragmentHomeBinding


class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVHomeAdapter = RVHomeAdapter()

        val btnscan=binding.btnScan
        val btnloc=binding.btnLog


        btnscan.setOnClickListener(){
            val intent = Intent(context, Scan::class.java)
            startActivity(intent)
        }

        btnloc.setOnClickListener(){
            val intent = Intent(context, Location::class.java)
            startActivity(intent)
        }

    }


}
