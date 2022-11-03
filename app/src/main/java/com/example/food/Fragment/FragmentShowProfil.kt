package com.example.food.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food.CameraPrev
import com.example.food.EditProfil
import com.example.food.MainActivity
import com.example.food.databinding.FragmentShowProfilBinding
import com.example.food.user.UserDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_show_profil.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentShowProfil : Fragment() {

    val db by lazy { activity?.let { UserDB(it) } }
    private val myPreference = "login"
    private val id = "idKey"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentShowProfilBinding? = null
    private val binding get() = _binding!!


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
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)


        val showUsername=binding.showUser
        val showEmail=binding.showEmail
        val showTanggal=binding.showTanggalLahir
        val showNomorHP=binding.showNomorHP
        val btnEdit=binding.btnEdit
        val btnOut=binding.btnExit




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

        btnPreview.setOnClickListener(){
            val intent = Intent(context, CameraPrev::class.java)
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