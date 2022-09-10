package com.example.food.entity

    class Makanan (var name: String, var harga : String){

        companion object{
            @JvmField
            var listOfMakanan = arrayOf(
                Makanan("Nasi Lemak Ayam", "Rp.20.000"),
                Makanan("Nasi Lemak Telur", "Rp.13.000"),
                Makanan("Soto Sapi", "Rp.25.000"),
                Makanan("Soto Ayam", "Rp.17.000"),
                Makanan("Soto Makasar", "Rp.25.000"),
                Makanan("Sate Ayam", "Rp.30.000"),
                Makanan("Bubur Ayam", "Rp.27.000"),
                Makanan("Nasi padang rendang", "Rp.20.000"),
                Makanan("Nasi Padang ayam rendang", "Rp.13.000"),
                Makanan("Nasi Padang daging cincang", "Rp.25.000"),
                Makanan("Nasi Padang ayam pop", "Rp.17.000"),
                Makanan("Nasi Padang ikan tuna", "Rp.25.000"),
                Makanan("Nasi Padang kepala ikan", "Rp.30.000"),
                Makanan("Nasi Padang telur ikan", "Rp.27.000"),
                )
        }
    }