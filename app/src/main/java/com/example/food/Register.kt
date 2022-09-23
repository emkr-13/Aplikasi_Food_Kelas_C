package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.food.databinding.ActivityMainBinding
import javax.xml.datatype.DatatypeConstants.MONTHS
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var BindingUser : ActivityMainBinding
    private lateinit var registerLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        BindingUser = ActivityMainBinding.inflate(layoutInflater)
        val viewBinding = BindingUser.root
        setContentView(viewBinding)

        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)
        val intent = Intent(this, MainActivity::class.java)
        BindingUser.btnLogin.setOnClickListener {
            startActivity(intent)
        }

        BindingUser.btnRegister.setOnClickListener{
            var  checkRegis = false
            val intent = Intent (this,MainActivity :: class.java)

            val username :String = BindingUser.inputLayoutUsername.editText?.getText().toString()
            val password : String = BindingUser.inputLayoutPassword.editText?.getText().toString()
//            val email : String = BindingUser.inputLayoutEmail.editText().toString()
//            val nomorHP=inputNomorHP.text.toString()
//            val tanggalLahir=inputTanggalLahir.text.toString()

            if (username.isEmpty()){
                BindingUser.inputLayoutPassword.setError("Username must be filled with text")
                checkRegis = false
            }
            if (password.isEmpty()){
                BindingUser.inputLayoutPassword.setError("Password must be filled with text")
                checkRegis = false
            }
//            if (email.isEmpty()){
//                inputEmail.setError("Email must be filled with text")
//                checkRegis = false
//            }
//            if (nomorHP.isEmpty()){
//                inputNomorHP.setError("nomorHP must be filled with text")
//                checkRegis = false
//            }
//            if (tanggalLahir.isEmpty()){
//                inputTanggalLahir.setError("Tanggal must be filled with text")
//                checkRegis = false
//            }
            else{
                checkRegis=true
            }
            if (!checkRegis)return@setOnClickListener
            val mBundle = Bundle()
//
//            mBundle.putString("username",inputUsername.text.toString())
//            mBundle.putString("password",inputPassword.text.toString())
//            mBundle.putString("email",inputEmail.text.toString())
//            mBundle.putString("tanggalLahir",inputTanggalLahir.text.toString())
//            mBundle.putString("nomorHp",inputNomorHP.text.toString())

            intent.putExtra("register", mBundle)

            startActivity(intent)

        }



        }
    }
