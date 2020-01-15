package com.yunlei.hindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = MAdapter()
        hIndicator.bindRecyclerView(recyclerView)


    }

    private inner class MAdapter : RecyclerView.Adapter<MViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
            return MViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_test,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = 30

        override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        }

    }

    private inner class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
