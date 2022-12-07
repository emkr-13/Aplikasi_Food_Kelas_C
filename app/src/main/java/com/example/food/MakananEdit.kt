package com.example.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.food.user.Constant
import com.example.food.user.Makanan

import com.example.food.user.UserDB
import kotlinx.android.synthetic.main.activity_makanan_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MakananEdit : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private var makanId: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan_edit)
        setupView()
        setupListener()
        Toast.makeText(this, makanId.toString(),Toast.LENGTH_SHORT).show()

    }

    fun setupView(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }



    private fun setupListener() {

        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.makananDao().addMakan(
                    Makanan(0,edit_title.text.toString(),edit_note.text.toString())
                )
                finish()
            }
        }

        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.makananDao().updateMakan(
                    Makanan(makanId,edit_title.text.toString(),
                        edit_note.text.toString())
                )
                finish()
            }

        }

    }
    fun getNote() {
        makanId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.makananDao().getDataMakan(makanId)[0]
            edit_title.setText(notes.name)
            edit_note.setText(notes.harga)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}