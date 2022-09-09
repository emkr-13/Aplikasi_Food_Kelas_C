package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    lateinit var  mBundle: Bundle

    lateinit var vUsername : String
    lateinit var vPassword : String
    lateinit var vEmail : String
    lateinit var vtanggalLahir : String
    lateinit var vNomorHP: String

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var loginLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("User Login")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        loginLayout = findViewById(R.id.loginLayout)

        val  btnClear: Button = findViewById(R.id.btnClear)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        var intent : Intent=intent
        if (intent.hasExtra("register")){
            getBudle()
            setText()
        }

        btnClear.setOnClickListener{
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            Snackbar.make(loginLayout,"Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnLogin.setOnClickListener(View.OnClickListener{
            var  checkLogin = false
            val username: String= inputUsername.getEditText()?.getText().toString()
            val password: String= inputPassword.getEditText()?.getText().toString()
            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }
            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if (username==vUsername && password==vPassword){
                checkLogin= true
            }
            if (username=="admin" && password=="0530"){
                checkLogin= true
            }
            if (!checkLogin)return@OnClickListener
            val moveHome = Intent(this@MainActivity,Home :: class.java)
            startActivity(moveHome)

        })

        btnRegister.setOnClickListener {
            val moveRegis = Intent(this@MainActivity,Register :: class.java)
            startActivity(moveRegis)
        }


    }


    fun  getBudle(){
            mBundle = intent.getBundleExtra("register")!!

            vUsername=mBundle.getString("username")!!
            vPassword=mBundle.getString("password")!!
            vEmail=mBundle.getString("email")!!
            vtanggalLahir=mBundle.getString("tanggalLahir")!!
            vNomorHP=mBundle.getString("nomorHp")!!
        }

    fun setText(){
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputUsername.getEditText()?.setText(vUsername)
    }
}