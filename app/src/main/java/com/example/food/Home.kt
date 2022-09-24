package com.example.food

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadFragment(FragmentHome())
        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.Home-> {
                    loadFragment(FragmentHome())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.list_makanan -> {
                    loadFragment(FragmentListMakanan())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.list_pesanan -> {
                    loadFragment(FragmentListPesanan())
                    return@setOnNavigationItemReselectedListener
                }

                R.id.data_profil -> {
                    loadFragment(FragmentShowProfil())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_exit -> {
                    loadFragment(FragmentExit())
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this@Home)
                    builder.setMessage("Are You Sure Want To Exit")
                        .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                            override fun onClick(dialogInterface: DialogInterface, i:Int) {
                                finishAndRemoveTask()
                            }
                        })
                        .show()
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