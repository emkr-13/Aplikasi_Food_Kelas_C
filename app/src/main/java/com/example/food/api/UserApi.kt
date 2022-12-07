package com.example.food.api

class UserApi {
    companion object {
//        isikan sesuai URl IP Anda
        val BASE_URL = "http://192.168.1.32/Aplikasi_food/ci4-apiserver/public/"

        val GET_ALL_URL = BASE_URL + "user/"
        val GET_BY_ID_URL = BASE_URL + "login/"
        val ADD_URL = BASE_URL + "register/"
        val UPDATE_URL = BASE_URL + "updateUser/"
    }
}