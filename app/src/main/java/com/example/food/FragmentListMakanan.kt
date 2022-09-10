package com.example.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.entity.Makanan


class FragmentListMakanan : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_makanan,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter: RVMakananAdapter = RVMakananAdapter(Makanan.listOfMakanan)

        val rvMakanan : RecyclerView = view.findViewById(R.id.rv_makanan)

        rvMakanan.layoutManager=layoutManager
        rvMakanan.setHasFixedSize(true)
        rvMakanan.adapter=adapter
    }
}