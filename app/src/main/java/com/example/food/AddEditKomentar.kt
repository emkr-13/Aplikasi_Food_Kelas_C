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
import com.example.food.api.KomentarApi

import com.example.food.model.komentar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_edit_komentar.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditKomentar : AppCompatActivity() {
    companion object{
        private val Jenis_LIST = arrayOf("Saran", "Kritik", "Complain")

    }
    private var edJenis: AutoCompleteTextView? = null
    private var etNama:  EditText?= null
    private var etNote: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_komentar)
        etNama=findViewById(R.id.et_nama)
        edJenis=findViewById(R.id.ed_jenis)
        etNote=findViewById(R.id.et_note)
        setExposedDropDownMenu()

        queue= Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancle)
        btnCancel.setOnClickListener {finish()}
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if(id==-1L) {
            tvTitle.setText("Tambah Komentar")
            btnSave.setOnClickListener { createKomentar() }
        } else {
            tvTitle.setText("Edit Komentar")
            getKomentarById(id)

            btnSave.setOnClickListener {updateKomentar(id)}
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
    fun setExposedDropDownMenu(){
        val adapterJenis: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.item_list, Jenis_LIST)
        ed_jenis!!.setAdapter(adapterJenis)
    }

    private fun getKomentarById(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, KomentarApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val makanan = gson.fromJson(jsonObject.getJSONObject("data").toString(), komentar::class.java)

                edJenis!!.setText(makanan.jenis_komentar)
                etNama!!.setText(makanan.nama_makanan)
                etNote!!.setText(makanan.cacatan)
                setExposedDropDownMenu()


                Toast.makeText(this@AddEditKomentar, "Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditKomentar,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditKomentar, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createKomentar(){
        setLoading(true)

        val mahasiswa = komentar(
            edJenis!!.text.toString(),
            etNama!!.text.toString(),
            etNote!!.text.toString(),
            )

        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, KomentarApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, KomentarApi::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditKomentar, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

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
                        this@AddEditKomentar,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@AddEditKomentar, e.message, Toast.LENGTH_SHORT).show()
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

    private fun updateKomentar(id: Long){
        setLoading(true)

        val mahasiswa = komentar(
            edJenis!!.text.toString(),
            etNama!!.text.toString(),
            etNote!!.text.toString(),

            )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, KomentarApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var mahasiswa = gson.fromJson(response, komentar::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@AddEditKomentar, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

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
                        this@AddEditKomentar,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditKomentar, e.message, Toast.LENGTH_SHORT).show()
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