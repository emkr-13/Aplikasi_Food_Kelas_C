package com.example.food

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

class EditProfil : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfilBinding
    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
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
        btnUpdate.setOnClickListener {
            var checkRegis = false
            val intent = Intent (this,FragmentShowProfil :: class.java)



            val username=inputUsername.text.toString()
            val password=inputPassword.text.toString()
            val email=inputEmail.text.toString()
            val nomorHP=inputNomorHP.text.toString()
            val tanggalLahir=inputTanggalLahir.text.toString()



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
                setupListener()
                Toast.makeText(applicationContext, username + " register", Toast.LENGTH_SHORT).show()
                checkRegis=true
            }
            if (!checkRegis)return@setOnClickListener





            val mBundle = Bundle()

            mBundle.putString("username",inputUsername.text.toString())
            mBundle.putString("password",inputPassword.text.toString())
            mBundle.putString("email",inputEmail.text.toString())
            mBundle.putString("tanggalLahir",inputTanggalLahir.text.toString())
            mBundle.putString("nomorHp",inputNomorHP.text.toString())

            intent.putExtra("register", mBundle)

            startActivity(intent)
        }
    }

    private fun setupListener(){
        val inputUsername=binding.ketikUsername.text.toString()
        val inputPassword=binding.ketikPassword.text.toString()
        val inputEmail=binding.ketikEmail.text.toString()
        val inputTanggalLahir=binding.ketikTanggalLahir.text.toString()
        val inputNomorHP=binding.ketikNomorHp.text.toString()
        val id = sharedPreferences?.getString("id", "")
        CoroutineScope(Dispatchers.IO).launch {

            db.userDao().updateUser(User(id!!.toInt(),inputUsername,inputPassword,inputEmail,inputNomorHP,inputTanggalLahir))

        }
        finish()

    }
}