package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class Register : AppCompatActivity() {
    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputEmail : TextInputEditText
    private  lateinit var  inputTanggalLahir: TextInputEditText
    private lateinit var  inputNomorHP: TextInputEditText


    private lateinit var btnRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister=findViewById(R.id.btnRegister)
        inputUsername=findViewById(R.id.ketikUsername)
        inputPassword=findViewById(R.id.ketikPassword)
        inputEmail=findViewById(R.id.ketikEmail)
        inputTanggalLahir=findViewById(R.id.ketikTanggalLahir)
        inputNomorHP=findViewById(R.id.ketikNomorHp)

        btnRegister.setOnClickListener{
            var  checkRegis = false
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
}