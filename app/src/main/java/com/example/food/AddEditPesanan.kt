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
import com.example.food.api.OrderApi
import com.example.food.model.food
import com.example.food.model.order
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_edit_pesanan.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditPesanan : AppCompatActivity() {

    private var etNama: EditText? = null
    private var etHarga:  EditText?= null
    private var etTotal: EditText? = null
    private var etNote: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_pesanan)
        etNama=findViewById(R.id.et_nama)
        etHarga=findViewById(R.id.et_harga)
        etTotal=findViewById(R.id.et_total)
        etNote=findViewById(R.id.et_note)


        queue= Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancle)
        btnCancel.setOnClickListener {finish()}
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if(id==-1L) {
            tvTitle.setText("Tambah Makanan")
            btnSave.setOnClickListener { createPesanan() }
        } else {
            tvTitle.setText("Edit Makanan")
            getPesananById(id)

            btnSave.setOnClickListener {updatePesanan(id)}
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

    private fun getPesananById(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, OrderApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val makanan = gson.fromJson(jsonObject.getJSONObject("data").toString(), order::class.java)

                etNama!!.setText(makanan.nama)
                etHarga!!.setText(makanan.harga)
                etTotal!!.setText(makanan.total_pesanan)
                etNote!!.setText(makanan.note_pesanan)


                Toast.makeText(this@AddEditPesanan, "Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditPesanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditPesanan, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createPesanan(){
        setLoading(true)

        val mahasiswa = order(
            etNama!!.text.toString(),
            etHarga!!.text.toString(),
            etTotal!!.text.toString(),
            etNote!!.text.toString(),


            )

        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, OrderApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, OrderApi::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditPesanan, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

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
                        this@AddEditPesanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@AddEditPesanan, e.message,Toast.LENGTH_SHORT).show()
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

    private fun updatePesanan(id: Long){
        setLoading(true)

        val mahasiswa = order(
            etNama!!.text.toString(),
            etHarga!!.text.toString(),
            etTotal!!.text.toString(),
            etNote!!.text.toString(),

            )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, OrderApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var mahasiswa = gson.fromJson(response, order::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditPesanan, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

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
                        this@AddEditPesanan,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditPesanan, e.message, Toast.LENGTH_SHORT).show()
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


