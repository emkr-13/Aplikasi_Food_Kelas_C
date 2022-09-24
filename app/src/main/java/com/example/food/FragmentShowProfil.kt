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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentShowProfil : Fragment() {

    val db by lazy { activity?.let { UserDB(it) } }

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
        sharedPreferences = this.getActivity()?.getSharedPreferences("login", Context.MODE_PRIVATE)
        val showUsername: TextView = view.findViewById(R.id.showUser)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val id = sharedPreferences?.getString("id", "")
        CoroutineScope(Dispatchers.IO).launch {
//            db?.userDao()?.getDataUser(id!!.toInt())
        }



        btnEdit.setOnClickListener() {
            val intent = Intent(context, EditProfil::class.java)
            startActivity(intent)
        }


    }
}