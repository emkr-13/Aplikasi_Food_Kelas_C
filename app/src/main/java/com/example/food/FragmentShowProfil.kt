package com.example.food

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.food.user.UserDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentShowProfil : Fragment() {

    val db by lazy { activity?.let { UserDB(it) } }
    private val myPreference = "login"
    private val id = "idKey"
    var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val showUsername: TextView = view.findViewById(R.id.showUser)
        val showEmail: TextView = view.findViewById(R.id.showEmail)
        val showTanggal: TextView = view.findViewById(R.id.showTanggalLahir)
        val showNomorHP: TextView = view.findViewById(R.id.showNomorHP)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnOut: Button = view.findViewById(R.id.btnExit)

        CoroutineScope(Dispatchers.IO).launch {
            val user= db?.userDao()?.getDataUser(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
            showUsername.setText(user?.user)
            showEmail.setText(user?.email)
            showTanggal.setText(user?.tanggalLahir)
            showNomorHP.setText(user?.nomorHP)
        }



        btnEdit.setOnClickListener() {
            val intent = Intent(context, EditProfil::class.java)
            startActivity(intent)
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


}