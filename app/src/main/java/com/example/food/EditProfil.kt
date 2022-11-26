package com.example.food

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.food.databinding.ActivityEditProfilBinding
import com.example.food.databinding.ActivityRegisterBinding
import com.example.food.user.User
import com.example.food.user.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfil : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfilBinding
    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "login"
    private val id = "idKey"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val  btnUpdate=binding.btnUpdate
        val inputUsername=binding.ketikUsername
        val inputPassword=binding.ketikPassword
        val inputEmail=binding.ketikEmail
        val inputTanggalLahir=binding.ketikTanggalLahir
        val inputNomorHP=binding.ketikNomorHp

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(id,"")!!.toInt()
        loadData(id)

        btnUpdate.setOnClickListener {
            var checkRegis = false
            val intent = Intent (this,Home :: class.java)



            val username=binding.ketikUsername.text.toString()
            val password=binding.ketikPassword.text.toString()
            val email=binding.ketikEmail.text.toString()
            val nomorHP=binding.ketikNomorHp.text.toString()
            val tanggalLahir=binding.ketikTanggalLahir.text.toString()



            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkRegis = false
            }
            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkRegis = false
            }
            if (email.isEmpty()){
                inputEmail.setError("Email must be filled with text")
                checkRegis = false
            }
            if (nomorHP.isEmpty()){
                inputNomorHP.setError("nomorHP must be filled with text")
                checkRegis = false
            }
            if (tanggalLahir.isEmpty()){
                inputTanggalLahir.setError("Tanggal must be filled with text")
                checkRegis = false
            }
            else{
//                setupListener()
                Toast.makeText(applicationContext, username + " Edit", Toast.LENGTH_SHORT).show()
                checkRegis=true
                updateUser()
            }
            if (!checkRegis)return@setOnClickListener



            startActivity(intent)




        }
    }

    private fun setupListener(){
        val inputUsername=binding.ketikUsername.text.toString()
        val inputPassword=binding.ketikPassword.text.toString()
        val inputEmail=binding.ketikEmail.text.toString()
        val inputTanggalLahir=binding.ketikTanggalLahir.text.toString()
        val inputNomorHP=binding.ketikNomorHp.text.toString()
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString(id, "")
        CoroutineScope(Dispatchers.IO).launch {

            db.userDao().updateUser(User(id!!.toInt(),inputUsername,inputPassword,inputEmail,inputNomorHP,inputTanggalLahir))

        }
        finish()
    }

    fun loadData(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getDataUser(id)?.get(0)


            withContext(Dispatchers.Main){
                binding.setUsername.editText?.setText(user?.user)
                binding.setPassword.editText?.setText(user?.password)
                binding.setEmail.editText?.setText(user?.email)
                binding.setTanggalLahir.editText?.setText(user?.tanggalLahir)
                binding.setNomorHp.editText?.setText(user?.nomorHP)

            }

        }
    }

    private fun updateUser(){

    }
}