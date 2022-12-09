package com.example.food.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.food.*
import com.example.food.api.UserApi
import com.example.food.databinding.FragmentShowProfilBinding
import com.example.food.model.user
import com.example.food.user.UserDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class FragmentShowProfil : Fragment() {

    val db by lazy { activity?.let { UserDB(it) } }
    private val myPreference = "login"
    private val id = "idKey"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentShowProfilBinding? = null
    private val binding get() = _binding!!

    var pref: SharedPreferences? = null
    private var queue: RequestQueue? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireActivity())
        val id = pref!!.getInt("id", -1)
        getUserById(id)

        val moveCam = Intent(getActivity(), CameraActivity::class.java)

        val showUsername=binding.showUser
        val showEmail=binding.showEmail
        val showTanggal=binding.showTanggalLahir
        val showNomorHP=binding.showNomorHP


        val btnEdit=binding.btnEdit
        val btnOut=binding.btnExit
        val btnCamera=binding.btnPreview
        val btnscan=binding.btnScan
        val btnVideo=binding.btnVideo

        btnscan.setOnClickListener(){
            val intent = Intent(context, Scan::class.java)
            startActivity(intent)
        }




//        CoroutineScope(Dispatchers.IO).launch {
//            val user= db?.userDao()?.getDataUser(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
//            showUsername.setText(user?.user)
//            showEmail.setText(user?.email)
//            showTanggal.setText(user?.tanggalLahir)
//            showNomorHP.setText(user?.nomorHP)
//        }



        btnEdit.setOnClickListener() {
            val intent = Intent(context, EditProfil::class.java)
            startActivity(intent)
        }

        btnVideo.setOnClickListener() {
            val intent = Intent(context, Video_YT::class.java)
            startActivity(intent)
        }

        btnCamera.setOnClickListener(){

            startActivity(moveCam)
        }

        btnOut.setOnClickListener {
            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Apakah Anda Ingin Keluar ?")
                    .setNegativeButton("No") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("yes") { dialog, which ->
                        val moveLogin = Intent(activity, MainActivity::class.java)
                        startActivity(moveLogin)
                        activity?.finish()
                    }
                    .show()
            }
        }


    }

    private fun getUserById(id: Int) {

//        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.getUserId + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var user = gson.fromJson(jsonObject.getJSONObject("data").toString(), user::class.java)
//                println(user.username)
//                println(user.email)
//                println(user.tanggal_lahir)

                binding.showUser.setText(user.username)
                binding.showEmail.setText(user.email)
                binding.showTanggalLahir.setText(user.tanggal_lahir)
                binding.showNomorHP.setText(user.telepon)

            }, Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(requireActivity(), errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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


}