package com.example.food.entity

import android.provider.ContactsContract
import com.example.food.R

class Pesanan (var name : String, var harga : String, var photo: Int) {
    companion object{
        @JvmField
        var listOfPesanan = arrayOf(
            Pesanan("Nasi padang rendang", "Rp.20.000", R.drawable.rendang),
            Pesanan("Nasi Padang ayam rendang", "Rp.13.000", R.drawable.ayamrendang),
            Pesanan("Nasi Padang daging cincang", "Rp.25.000", R.drawable.dagingcincang),
            Pesanan("Nasi Padang ayam pop", "Rp.17.000",R.drawable.ayampop),
            Pesanan("Nasi Padang ikan tuna", "Rp.25.000",R.drawable.ikantuna),
            Pesanan("Nasi Padang kepala ikan", "Rp.30.000",R.drawable.kepalaikan),
            Pesanan("Nasi Padang telur ikan", "Rp.27.000",R.drawable.telurikan),
        )
    }
}