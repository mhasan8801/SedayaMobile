package com.app.sedaya.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.sedaya.R
import com.app.sedaya.activity.DetailSeniActivity
import com.app.sedaya.helper.Helper
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterSeni(var activity: Activity, var data: ArrayList<Seni>) : RecyclerView.Adapter<AdapterSeni.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul = view.findViewById<TextView>(R.id.tv_judul)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgSeni = view.findViewById<ImageView>(R.id.img_seni)
        val layout = view.findViewById<CardView>(R.id.layout_seni)

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
        holder.tvHarga.text = Helper().gantiRp(data[position].harga)
//        holder.imgSeni.setImageResource(data[position].gambar)

        val gambar = "http://ws-tif.com/sedaya/admin/public/img/seni/"+data[position].gambar
        Picasso.get()
            .load(gambar)
            .placeholder(R.drawable.loadingseni)
            .error(R.drawable.loadingseni)
            .into(holder.imgSeni)

        holder.layout.setOnClickListener {
            val activiti = Intent(activity, DetailSeniActivity::class.java)

            val string = Gson().toJson(data[position], Seni::class.java)
            activiti.putExtra("extra", string)
            activity.startActivity(activiti)
        }
    }
}