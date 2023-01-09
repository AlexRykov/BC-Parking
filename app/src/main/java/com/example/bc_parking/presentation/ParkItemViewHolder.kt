package com.example.bc_parking.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bc_parking.R

class ParkItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = view.findViewById<TextView>(R.id.tvName)
    val tvFirm: TextView = view.findViewById<TextView>(R.id.tvFirm)
    val tvDateFrom: TextView = view.findViewById<TextView>(R.id.tvDateFrom)
    val tvDateTo: TextView = view.findViewById<TextView>(R.id.tvDateTo)
    val tvCount: TextView = view.findViewById<TextView>(R.id.tvCount)
}