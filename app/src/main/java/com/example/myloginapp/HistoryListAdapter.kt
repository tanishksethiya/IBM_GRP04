package com.example.myloginapp;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myloginapp.R

class HistoryListAdapter(context1: Context, private val onUrlClick: onUrlClick?) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    val context = context1
    val userDao = WebHistoryDatabase.getInstance(context)?.webHistoryDao()
    var mList = userDao?.getAll()
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_history_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList?.get(position)
        holder.id.text = ItemsViewModel?.userId.toString()

        holder.title.text = ItemsViewModel?.title
        holder.link.text = ItemsViewModel?.url



        if(ItemsViewModel?.status.equals("Safe")){
            holder.title.setTextColor(Color.parseColor("#00FF0A"))
        }else{
            holder.title.setTextColor(Color.parseColor("#F30808"))
        }


        holder.link.setOnClickListener {
            onUrlClick?.openLinkInBrowser(ItemsViewModel?.url)
        }

        holder.imgDelete.setOnClickListener {
            onUrlClick?.deleteHistory(ItemsViewModel?.userId)
        }
    }

    public fun refreshList(){
        notifyDataSetChanged()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList?.size?:0
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView :CardView = itemView.findViewById(R.id.cardView)
        val id: TextView = itemView.findViewById(R.id.tv_id)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val link: TextView = itemView.findViewById(R.id.tv_link)
        val imgDelete: AppCompatImageView = itemView.findViewById(R.id.iv_delete)
    }
}