package com.app.sedaya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.sedaya.R
import com.app.sedaya.model.Seni

class AdapterSeni (var data: ArrayList<Seni>) : RecyclerView.Adapter<AdapterSeni.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul = view.findViewById<TextView>(R.id.tv_judul)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgSeni = view.findViewById<ImageView>(R.id.img_seni)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_seni, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvJudul.text = data[position].judul
        holder.tvHarga.text = data[position].harga
        holder.imgSeni.setImageResource(data[position].image)
    }
}