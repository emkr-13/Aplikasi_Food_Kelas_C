package com.example.food.api

class MakananApi {
    companion object {
//        Isi Berdasarkan URL IP Anda
        val BASE_URL = "http://192.168.1.32/Aplikasi_food/ci4-apiserver/public/"

        val GET_ALL_URL = BASE_URL + "makanan/"
        val GET_BY_ID_URL = BASE_URL + "makanan/"
        val ADD_URL = BASE_URL + "makanan"
        val UPDATE_URL = BASE_URL + "makanan/"
        val DELETE_URL = BASE_URL + "makanan/"
    }
}