package com.example.food

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.entity.Pesanan
import kotlinx.android.synthetic.main.fragment_list_pesanan.*



class FragmentListPesanan : Fragment() {
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
        val adapter: RVPesananAdapter = RVPesananAdapter(Pesanan.listOfPesanan)

        val rvPesanan: RecyclerView = view.findViewById(R.id.rv_pesanan)

        rvPesanan.layoutManager = layoutManager
        rvPesanan.setHasFixedSize(true)
        rvPesanan.adapter = adapter



    }


}
