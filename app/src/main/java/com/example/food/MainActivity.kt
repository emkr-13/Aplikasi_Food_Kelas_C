package com.example.food

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.food.user.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "login"
    private val id = "idKey"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Login")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        loginLayout = findViewById(R.id.loginLayout)

        val  btnClear: Button = findViewById(R.id.btnClear)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)


        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)

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
        val moveHome = Intent(this@MainActivity,Home :: class.java)


        btnLogin.setOnClickListener(View.OnClickListener{
            var  checkLogin = false
            val username: String= inputUsername.getEditText()?.getText().toString()
            val password: String= inputPassword.getEditText()?.getText().toString()

            CoroutineScope(Dispatchers.IO).launch {
                val users = db.userDao().getUser()
                Log.d("MainActivity ","dbResponse: $users")

                for(i in users){
                    if(username == i.user && password ==i.password){
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(id, i.id.toString())
                        editor.apply()
                        checkLogin=true
                        break
                    }
                }

                withContext(Dispatchers.Main){
                    if((username == "admin" && password== "admin") || (checkLogin)){
                        checkLogin = false

                        startActivity(moveHome)
                        finish()
                    }
                }

            }

            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }
            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }



            if (!checkLogin)return@OnClickListener



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
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputPassword.getEditText()?.setText(vPassword)
    }
}