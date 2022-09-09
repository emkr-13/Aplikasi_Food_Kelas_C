package com.example.food.entity

class Pesanan (var name : String, var harga : String) {
    companion object{
        @JvmField
        var listOfPesanan = arrayOf(
            Pesanan("Nasi padang rendang", "Rp.20.000"),
            Pesanan("Nasi Padang ayam rendang", "Rp.13.000"),
            Pesanan("Nasi Padang daging cincang", "Rp.25.000"),
            Pesanan("Nasi Padang ayam pop", "Rp.17.000"),
            Pesanan("Nasi Padang ikan tuna", "Rp.25.000"),
            Pesanan("Nasi Padang kepala ikan", "Rp.30.000"),
            Pesanan("Nasi Padang telur ikan", "Rp.27.000"),
        )
    }
}