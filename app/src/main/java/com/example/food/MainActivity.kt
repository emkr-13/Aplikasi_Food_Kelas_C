package com.example.food

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    private val ids = "idKey"
    private val CHANNEL_ID_LOGIN = "channel_notification_02"
    private val notificationId2 = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setTitle("Login")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        loginLayout = findViewById(R.id.loginLayout)


        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)


        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
//        val ids = sharedPreferences!!.getString(ids,"")!!.toInt()
//        loadData(ids)
//        CoroutineScope(Dispatchers.IO).launch{
//            if(db.userDao().getUser().isNotEmpty()){
//
//            }
//        }

        var intent : Intent=intent
        if (intent.hasExtra("register")){
            getBudle()
            setText()
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

                        createNotificationChannel()
                        sendNotification2(username)
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
            .setSmallIcon(R.drawable.ic_baseline_arrow_back_24)
            .setContentTitle("Berhasil Login "+  username   +" di FOOD")
            .setContentText("Selamat datang di FOOD")
            .setStyle( NotificationCompat.BigTextStyle().bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"))
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


}