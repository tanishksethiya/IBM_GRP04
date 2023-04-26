package com.example.myloginapp;

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myloginapp.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    lateinit var binder: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.btnSearch.setOnClickListener {
            val intent1 = Intent(this, MainActivityWebview::class.java)
            startActivity(intent1)
        }

        binder.btnHistory.setOnClickListener {
            val intent2 = Intent(this, ListActivity::class.java)
            startActivity(intent2)
        }

        binder.btnClearAllHistory.setOnClickListener {
            val userDao = WebHistoryDatabase.getInstance(this)?.webHistoryDao()
            userDao?.deleteAll()

            val myToast = Toast.makeText(applicationContext,"Web History Cleared", Toast.LENGTH_SHORT)
            myToast.setGravity(Gravity.LEFT,200,200)
            myToast.show()
        }
    }
}