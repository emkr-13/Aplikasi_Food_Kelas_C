package com.example.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.food.Fragment.FragmentHome
import com.example.food.Fragment.FragmentListMakanan
import com.example.food.Fragment.FragmentListPesanan
import com.example.food.Fragment.FragmentShowProfil
import com.example.food.databinding.ActivityHomeBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        loadFragment(FragmentHome())

        bottomNav = binding.bottomNavigationView  as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.Home-> {
                    loadFragment(FragmentHome())
                    frame_layout.setPadding(0,0,0,0)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.list_makanan -> {
                    loadFragment(FragmentListMakanan())
                    frame_layout.setPadding(0,0,0,0)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.list_pesanan -> {
                    loadFragment(FragmentListPesanan())
                    frame_layout.setPadding(0,0,0,0)
                    return@setOnNavigationItemReselectedListener
                }

                R.id.data_profil -> {
                    loadFragment(FragmentShowProfil())
                    frame_layout.setPadding(0,0,0,0)
                    return@setOnNavigationItemReselectedListener
                }

            }
        }


    }


    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }




}