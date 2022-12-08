package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.food.api.MakananApi
import com.example.food.model.food
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditMakanan : AppCompatActivity() {


    private var etNama: EditText? = null
    private var etHarga: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_makanan)

        etNama=findViewById(R.id.et_nama)
        etHarga=findViewById(R.id.et_harga)


        queue=Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancle)
        btnCancel.setOnClickListener {finish()}
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if(id==-1L) {
            tvTitle.setText("Tambah Makanan")
            btnSave.setOnClickListener { createMakanan() }
        } else {
            tvTitle.setText("Edit Makanan")
            getMakananById(id)

            btnSave.setOnClickListener {updateMakanan(id)}
        }
    }
    private fun setLoading(isLoading: Boolean) {
        if(isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
    private fun getMakananById(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, MakananApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, food::class.java)

                etNama!!.setText(mahasiswa.nama)
                etHarga!!.setText(mahasiswa.harga)


                Toast.makeText(this@AddEditMakanan, "Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditMakanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditMakanan, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createMakanan(){     setLoading(true)

        val mahasiswa = food(
            etNama!!.text.toString(),
            etHarga!!.text.toString(),


        )

        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, MakananApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, MakananApi::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditMakanan, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditMakanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@AddEditMakanan, e.message,Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(mahasiswa)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    private fun updateMakanan(id: Long){
        setLoading(true)

        val mahasiswa = food(
            etNama!!.text.toString(),
            etHarga!!.text.toString(),

        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, MakananApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var mahasiswa = gson.fromJson(response, food::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditMakanan, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditMakanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditMakanan, e.message, Toast.LENGTH_SHORT).show()
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
                val requestBody = gson.toJson(mahasiswa)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}