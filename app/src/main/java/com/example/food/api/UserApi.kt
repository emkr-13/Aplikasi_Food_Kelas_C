package com.example.food.api

class UserApi {
    companion object {
//        isikan sesuai URl IP Anda
//        nantikan pakai hosting pakai local dulu
        val BASE_URL = "http://192.168.1.10/Aplikasi_food/ci4-apiserver/public/"

        val register = BASE_URL + "register"
        val login = BASE_URL + "login"
        val getUserId = BASE_URL + "user/"
        val updateUser = BASE_URL + "user/"
    }
}