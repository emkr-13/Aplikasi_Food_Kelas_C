package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.food.databinding.ActivityRegisterBinding
import com.example.food.user.User
import com.example.food.user.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.xml.datatype.DatatypeConstants.MONTHS
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val  btnRegister=binding.btnRegister
        val inputUsername=binding.ketikUsername
        val inputPassword=binding.ketikPassword
        val inputEmail=binding.ketikEmail
        val inputTanggalLahir=binding.ketikTanggalLahir
        val inputNomorHP=binding.ketikNomorHp


        btnRegister.setOnClickListener {
            var checkRegis = false
            val intent = Intent (this,MainActivity :: class.java)



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
                checkRegis=true
            }
            if (!checkRegis)return@setOnClickListener

            setupListener()
            Toast.makeText(applicationContext, username + " register", Toast.LENGTH_SHORT).show()



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

//

    private fun setupListener(){
        val inputUsername=binding.ketikUsername.text.toString()
        val inputPassword=binding.ketikPassword.text.toString()
        val inputEmail=binding.ketikEmail.text.toString()
        val inputTanggalLahir=binding.ketikTanggalLahir.text.toString()
        val inputNomorHP=binding.ketikNomorHp.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

            db.userDao().addUser(User(0,inputUsername,inputPassword,inputEmail,inputNomorHP,inputTanggalLahir))

        }
        finish()

    }
}




