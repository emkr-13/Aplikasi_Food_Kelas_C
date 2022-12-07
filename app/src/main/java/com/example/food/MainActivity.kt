package com.example.food

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.food.api.UserApi
import com.example.food.databinding.ActivityMainBinding
import com.example.food.model.LoginUser
import com.example.food.user.UserDB
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets


//Login Ini Login
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var  mBundle: Bundle

    lateinit var vUsername : String
    lateinit var vPassword : String


    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var loginLayout: ConstraintLayout

    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "login"
    private val id = "idKey"
    private val ids = "idKey"
    private val key = "nameKey"
    private val CHANNEL_ID_LOGIN = "channel_notification_02"
    private val notificationId2 = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
        val view = binding.root

        inputUsername = binding.inputLayoutUsername
        inputPassword = binding.inputLayoutPassword
        loginLayout = binding.loginLayout

        val btnLogin = binding.btnLogin
        val btnRegister=binding.btnRegister
        val moveHome = Intent(this@MainActivity,Home :: class.java)

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        LoginApp()
//      yang Bawah Ini Gak Usaha di Pakai
//        Load Data Error if with
//        val ids = sharedPreferences!!.getString(ids,"")!!.toInt()
//        loadData(ids)
//        CoroutineScope(Dispatchers.IO).launch{
//            if(db.userDao().getUser().isNotEmpty()){
//
//            }
//        }


        if(!sharedPreferences!!.contains(key)){
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString(key, "terisi")
            editor.apply()
            setContentView(R.layout.activity_splash_screen)

            Handler(Looper.getMainLooper()).postDelayed({
                setContentView(view)
            }, 3000)
        }else{
            setContentView(view)
        }




//    Ini Btn Login
        btnLogin.setOnClickListener(View.OnClickListener{

            var  checkLogin = false
            val username: String= inputUsername.getEditText()?.getText().toString()
            val password: String= inputPassword.getEditText()?.getText().toString()

            var intent : Intent=intent
            if (intent.hasExtra("register")){
                getBudle()
                setText()
            }
//            Ini Room Database


            CoroutineScope(Dispatchers.IO).launch {
                val users = db.userDao().getUser()
                Log.d("MainActivity ","dbResponse: $users")

                for(i in users){
                    if(username == i.user && password ==i.password){
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(id, i.id.toString())
                        editor.apply()
                        checkLogin=true
                        //import libary sucess
//                        FancyToast.makeText(this@MainActivity,"Login Sucsess !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true)
                        createNotificationChannel()
                        sendNotification2(username)
                        break
                    }
                }

                withContext(Dispatchers.Main){
                    if((username == "admin" && password== "admin") || (checkLogin)){
                        checkLogin = false
                        //import libary sucess
//                        FancyToast.makeText(this@MainActivity,"Login Sucsess !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true);
                        startActivity(moveHome)
                        finish()
                    }
                }

            }

            if (username.isEmpty()){
               inputUsername.setError("Username must be filled with text")
                //import libary error
//                FancyToast.makeText(
//                    this,
//                    "Username must be filled with text",
//                    FancyToast.LENGTH_LONG,
//                    FancyToast.ERROR,
//                    true
//                ).show()
                checkLogin = false
            }
            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                //import libary error
//                FancyToast.makeText(
//                    this,
//                    "Password must be filled with text",
//                    FancyToast.LENGTH_LONG,
//                    FancyToast.ERROR,
//                    true
//                ).show()
                checkLogin = false
            }

            if (!checkLogin)return@OnClickListener
//            LoginApp()
        })
//        Ini BTN register
        btnRegister.setOnClickListener {
            val moveRegis = Intent(this@MainActivity,Register :: class.java)
            startActivity(moveRegis)
        }


    }


    fun  getBudle(){
            mBundle = intent.getBundleExtra("register")!!

            vUsername=mBundle.getString("username")!!
            vPassword=mBundle.getString("password")!!
        }

    fun setText(){
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputUsername.getEditText()?.setText(vUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputPassword.getEditText()?.setText(vPassword)
    }

// Udah Ke pakai
    fun loadData(id: Int){
        inputUsername = findViewById(R.id.inputLayoutUsername)


        inputPassword = findViewById(R.id.inputLayoutPassword)
        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getDataUser(id)?.get(0)


            withContext(Dispatchers.Main){

                inputUsername.editText?.setText(user?.user)
                inputPassword.editText?.setText(user?.password)
            }

        }
    }
// Notifikasi
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Register"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_LOGIN,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification2(username: String){
        val intent : Intent = Intent (this, Home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)


        val okIntent = Intent(this, Home::class.java)
        okIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val okPendingIntent= PendingIntent.getActivity(this,0,okIntent,PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_LOGIN)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle("Berhasil Login "+  username   +" di FOOD")
            .setContentText("Selamat datang di FOOD")
            .setStyle( NotificationCompat.BigTextStyle().bigText("Login anda berhasil jangan lupa patuhi protokol kesehatan dan juga jangan lupa mencuci tangan demi kenyamanan anda dalam menyantap makanan dan selamat berbelanja"))
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .addAction(R.mipmap.ic_launcher, "Ok", okPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_EMAIL)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId2, builder.build())
        }



    }
//LoginJSON
    private fun LoginApp(){
        val userLogin = LoginUser(
            binding.inputLayoutUsername.editText?.getText().toString(),
            binding.inputLayoutPassword.editText?.getText().toString()

        )
//    Log.d("cekRegister", userLogin.toString())
    val user: StringRequest =
        object : StringRequest(Method.PUT, UserApi.GET_BY_ID_URL, Response.Listener { response ->
            val gson = Gson()
            var login = gson.fromJson(response, userLogin::class.java)
            val jsonObject = JSONObject(response)
            if (login != null) {
                Toast.makeText(this@MainActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
            }
            val moveHome = Intent(this@MainActivity, Home::class.java)
            val userID : SharedPreferences.Editor = sharedPreferences!!.edit()
            userID.putInt("id", jsonObject.getJSONObject("user").getInt("id"))
            userID.apply()
            startActivity(moveHome)
            finish()

        },Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors = JSONObject(responseBody)
                Toast.makeText(
                    this@MainActivity,
                    errors.getString("message"),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }

        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(userLogin)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }


}

}

