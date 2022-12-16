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

import com.example.food.adapater.OrderAdapater

import com.example.food.api.OrderApi

import com.example.food.model.order
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class PesananList : AppCompatActivity() {
    private var srPesanan: SwipeRefreshLayout? = null
    private var adapter: OrderAdapater? = null
    private var svPesanan: SearchView? = null
    private  var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_list)
        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srPesanan = findViewById(R.id.sr_pesanan)
        svPesanan = findViewById(R.id.sv_pesanan)

        srPesanan?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allPesanan() })
        svPesanan?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
            val i = Intent(this@PesananList, AddEditPesanan::class.java)
            startActivityForResult(i, MakananList.LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_pesanan)
        adapter = OrderAdapater(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allPesanan()

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

    private fun allPesanan() {
        srPesanan!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, OrderApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var foods: Array<order> = gson.fromJson(
                    jsonObject.getJSONArray("data").toString(),
                    Array<order>::class.java
                )

                adapter!!.setPesananList(foods)
                adapter!!.filter.filter(svPesanan!!.query)
                srPesanan!!.isRefreshing = false

                if (!foods.isEmpty())
                    Toast.makeText(this@PesananList, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@PesananList, "Data Kosong!", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                srPesanan!!.isRefreshing = false
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@PesananList,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@PesananList, e.message, Toast.LENGTH_SHORT).show()
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

    fun deletePesanan(id:Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.DELETE,
                OrderApi.DELETE_URL + id,
                Response.Listener { response ->
                    setLoading(false)

                    val gson = Gson()
                    var mahasiswa = gson.fromJson(response, order::class.java)
                    if (mahasiswa != null)
                        Toast.makeText(
                            this@PesananList,
                            "Data Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        )
                    allPesanan()
                },
                Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@PesananList,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(this@PesananList, e.message, Toast.LENGTH_SHORT)
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
        if(requestCode == MakananList.LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allPesanan()
    }
}