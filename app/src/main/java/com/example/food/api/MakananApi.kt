package com.example.food.api

class MakananApi {
    companion object {
//        Isi Berdasarkan URL IP Anda
//        nanti di pakai hosting untuk serve
        val BASE_URL = "http://api.tugaskampus13.my.id/api/"

        val GET_ALL_URL = BASE_URL + "makanan"
        val GET_BY_ID_URL = BASE_URL + "makanan/"
        val ADD_URL = BASE_URL + "makanan"
        val UPDATE_URL = BASE_URL + "makanan/"
        val DELETE_URL = BASE_URL + "makanan/"
    }
}