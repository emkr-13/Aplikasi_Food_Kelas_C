package com.example.food.api

class UserApi {
    companion object {
//        isikan sesuai URl IP Anda
        val BASE_URL = ""

        val GET_ALL_URL = BASE_URL + "user/"
        val GET_BY_ID_URL = BASE_URL + "login/"
        val ADD_URL = BASE_URL + "register"
        val UPDATE_URL = BASE_URL + "updateUser/"
    }
}