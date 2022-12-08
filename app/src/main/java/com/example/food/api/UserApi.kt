package com.example.food.api

class UserApi {
    companion object {
//        isikan sesuai URl IP Anda
//        nantikan pakai hosting pakai local dulu
        val BASE_URL = "http://192.168.100.5/api-food/public/api/"

        val register = BASE_URL + "register"
        val login = BASE_URL + "login"
        val getUserId = BASE_URL + "user/"
        val updateUser = BASE_URL + "user/"
        val GET_ALL_URL = BASE_URL+"user"
    }
}