package com.example.myloginapp;

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myloginapp.R
import com.example.myloginapp.databinding.ActivityListBinding


class ListActivity : AppCompatActivity() {

    lateinit var binder: ActivityListBinding
     var adapter: HistoryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityListBinding.inflate(layoutInflater)
        setContentView(binder.root)

//        val actionBar: ActionBar? = supportActionBar
//        actionBar!!.setDisplayHomeAsUpEnabled(true)

        // this creates a vertical layout Manager
        binder.rvHistory.layoutManager = LinearLayoutManager(this)

        val userDao = WebHistoryDatabase.getInstance(this)?.webHistoryDao()

        // ArrayList of class ItemsViewModel
        val data: List<WebHistory>? = userDao?.getAll()
        // This will pass the ArrayList to our Adapter
       adapter = HistoryListAdapter(this,object : onUrlClick {
            override fun openLinkInBrowser(url: String?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

            override fun deleteHistory(userId: Int?) {
//              Int   val builder = AlertDialog.Builder(this@ListActivity, R.style.CustomAlertDialog)
//
//
//                builder.setTitle("Abcd")
//                builder.setMessage("Egf")
//                builder.setPositiveButton("Yes") { dialogInterface, which ->
//                    userDao?.delete(userId!!)
//                    adapter?.refreshList()
//                    dialogInterface.dismiss()
//                }
//                builder.setNegativeButton("No") { dialogInterface, which ->
//                    dialogInterface.dismiss()
//                }
//                val alertDialog: AlertDialog = builder.create()
//                alertDialog.setCancelable(false)
//                alertDialog.show()
//
//                val negativeButton: Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
//                val positiveButton: Button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            }
        })
        // Setting the Adapter with the recyclerview
        binder.rvHistory.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}