package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.CameraPrev
import com.example.food.Location
import com.example.food.R
import com.example.food.RVHomeAdapter
import com.example.food.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


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

        val btnloc=binding.btnLog

        btnloc.setOnClickListener(){
            val intent = Intent(context, Location::class.java)
            startActivity(intent)
        }


    }


}
