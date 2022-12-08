package com.example.food.api

class MakananApi {
    companion object {
//        Isi Berdasarkan URL IP Anda
//        nanti di pakai hosting untuk serve
        val BASE_URL = "http://192.168.100.5/api-food/public/api/"

        val GET_ALL_URL = BASE_URL + "makanan"
        val GET_BY_ID_URL = BASE_URL + "makanan/"
        val ADD_URL = BASE_URL + "makanan"
        val UPDATE_URL = BASE_URL + "makanan/"
        val DELETE_URL = BASE_URL + "makanan/"
    }
}