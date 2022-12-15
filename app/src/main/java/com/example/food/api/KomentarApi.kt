package com.example.food.api

class KomentarApi {
    companion object {

        //        Isi Berdasarkan URL IP Anda
        
//        nanti di pakai hosting untuk serve
        val BASE_URL = "http://192.168.100.5/api-food/public/api/"

        val GET_ALL_URL = BASE_URL + "komentar"
        val GET_BY_ID_URL = BASE_URL + "komentar/"
        val ADD_URL = BASE_URL + "komentar"
        val UPDATE_URL = BASE_URL + "komentar/"
        val DELETE_URL = BASE_URL + "komentar/"


    }
}