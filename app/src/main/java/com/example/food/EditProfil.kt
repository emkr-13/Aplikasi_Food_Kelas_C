package com.example.food

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.food.api.UserApi
import com.example.food.databinding.ActivityEditProfilBinding
import com.example.food.databinding.ActivityRegisterBinding
import com.example.food.model.editUser
import com.example.food.model.user
import com.example.food.user.User
import com.example.food.user.UserDB
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*

class EditProfil : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfilBinding

    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "login"
    private val id = "idKey"

    var pref: SharedPreferences? = null
    private var queue: RequestQueue? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        pref = getSharedPreferences("prefId", Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(this)

        val id = pref!!.getInt("id", -1)
        getUserById(id)

        val  btnUpdate=binding.btnUpdate

        val inputUsername=binding.ketikUsername
        val inputEmail=binding.ketikEmail
        val inputTanggalLahir=binding.ketikTanggalLahir
        val inputNomorHP=binding.ketikNomorHp



        inputTanggalLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val date = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    inputTanggalLahir.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)

                }, year, month, day)

            date.show()
        }



        btnUpdate.setOnClickListener {
            var checkRegis = false



            val username=binding.ketikUsername.text.toString()

            val email=binding.ketikEmail.text.toString()
            val nomorHP=binding.ketikNomorHp.text.toString()
            val tanggalLahir=binding.ketikTanggalLahir.text.toString()



            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
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

            }
            updateUser(id)

            if (!checkRegis)return@setOnClickListener





        }
    }


    private  fun getUserById(id: Int){
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.getUserId + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var user = gson.fromJson(jsonObject.getJSONObject("data").toString(), user::class.java)
                binding.ketikUsername.setText(user.username)
                binding.ketikEmail.setText(user.email)
                binding.ketikTanggalLahir.setText(user.tanggal_lahir)
                binding.ketikNomorHp.setText(user.telepon)

            }, Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        queue!!.add(stringRequest)
    }

    private fun updateUser(id: Int){

        val user = editUser(
            binding.ketikUsername.text.toString(),
            binding.ketikEmail.text.toString(),
            binding.ketikTanggalLahir.text.toString(),
            binding.ketikNomorHp.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, UserApi.updateUser + id, Response.Listener { response ->
                val gson = Gson()

                var user = gson.fromJson(response, editUser::class.java)

                if(user != null)
                    Toast.makeText(this, "Data Berhasil diUpdate", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Home::class.java)


                startActivity(intent)
                finish()
            }, Response.ErrorListener { error ->
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@EditProfil,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@EditProfil, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)

    }
}