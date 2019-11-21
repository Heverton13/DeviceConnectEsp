package com.example.deviceconnectesp.scan

import android.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val device: TextView = itemView.findViewById(R.id.text1)
    val rssi: TextView = itemView.findViewById(R.id.text2)
}
