package com.example.food

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.food.databinding.ActivityRegisterBinding
import com.example.food.user.User
import com.example.food.user.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.Cleaner
import javax.xml.datatype.DatatypeConstants.MONTHS
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val db by lazy { UserDB(this) }
    private val CHANNEL_ID_REGISTER = "channel_notification_01"
    private val notificationId1 = 101

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
//                ini buat room
//            setupListener()
//            ini buat JSON
            regis()

            Toast.makeText(applicationContext, username + " register", Toast.LENGTH_SHORT).show()

            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logohome)
            createNotificationChannel()

            sendNotification1(username,bitmap)


            val mBundle = Bundle()

            mBundle.putString("username",username)
            mBundle.putString("password",password)
            mBundle.putString("email",email)
            mBundle.putString("tanggalLahir",tanggalLahir)
            mBundle.putString("nomorHp",nomorHP)

            intent.putExtra("register", mBundle)

            startActivity(intent)



        }
    }

//    Ini register Room
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
//Notifikasi
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Register"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_REGISTER,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }
//Notifkasi
    private fun sendNotification1(username: String, bitmap : Bitmap){
        val intent : Intent = Intent (this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val okIntent = Intent(this, MainActivity::class.java)
        okIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val okPendingIntent= PendingIntent.getActivity(this,0,okIntent,PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_REGISTER)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle("Registrasi Berhasil")
            .setContentText("Halo " + username + " Kamu Berhasil Registrasi")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
//            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Ok", okPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))



        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }



    }

//ini RegisWeb
    private  fun regis(){

    }
}




