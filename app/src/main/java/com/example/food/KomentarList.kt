package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.food.adapater.KomentarAdapater

import com.example.food.api.KomentarApi

import com.example.food.model.komentar

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class KomentarList : AppCompatActivity() {
    private var srKomentar: SwipeRefreshLayout? = null
    private var adapter: KomentarAdapater? = null
    private var svKomentar: SearchView? = null
    private  var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komentar_list)
        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srKomentar = findViewById(R.id.sr_komentar)
        svKomentar = findViewById(R.id.sv_komentar)

        srKomentar?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allKomentar() })
        svKomentar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean{
                return false
            }

            override fun onQueryTextChange(s: String): Boolean{
                adapter!!.filter.filter(s)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener{
            val i = Intent(this@KomentarList, AddEditKomentar::class.java)
            startActivityForResult(i, MakananList.LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_komentar)
        adapter = KomentarAdapater(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allKomentar()
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
    private fun allKomentar() {
        srKomentar!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, KomentarApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var foods: Array<komentar> = gson.fromJson(
                    jsonObject.getJSONArray("data").toString(),
                    Array<komentar>::class.java
                )

                adapter!!.setKomentarList(foods)
                adapter!!.filter.filter(svKomentar!!.query)
                srKomentar!!.isRefreshing = false

                if (!foods.isEmpty())
                    Toast.makeText(this@KomentarList, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@KomentarList, "Data Kosong!", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                srKomentar!!.isRefreshing = false
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@KomentarList,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@KomentarList, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }
    fun deleteKomentar(id:Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.DELETE,
                KomentarApi.DELETE_URL + id,
                Response.Listener { response ->
                    setLoading(false)

                    val gson = Gson()
                    var mahasiswa = gson.fromJson(response, komentar::class.java)
                    if (mahasiswa != null)
                        Toast.makeText(
                            this@KomentarList,
                            "Data Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        )
                    allKomentar()
                },
                Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@KomentarList,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(this@KomentarList, e.message, Toast.LENGTH_SHORT)
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == KomentarList.LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allKomentar()
    }


}