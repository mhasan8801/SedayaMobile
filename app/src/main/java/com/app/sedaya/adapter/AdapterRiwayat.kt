package com.app.sedaya.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.sedaya.R
import com.app.sedaya.activity.DetailSeniActivity
import com.app.sedaya.activity.DetailTransaksiActivity
import com.app.sedaya.activity.LoginActivity
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.activity.RiwayatActivity
import com.app.sedaya.databinding.ActivityMasukBinding
import com.app.sedaya.databinding.ActivityRiwayatBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.History
import com.app.sedaya.model.ResponModel
import com.app.sedaya.helper.Helper
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterRiwayat(var activity: Activity, var data: ArrayList<History>) : RecyclerView.Adapter<AdapterRiwayat.Holder>() {


    lateinit var sP: SharedPref
    lateinit var riwayat: RiwayatActivity
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul = view.findViewById<TextView>(R.id.tv_judul)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harganilai)
        val tvTransport = view.findViewById<TextView>(R.id.tv_transportnilai)
        val tvTotal = view.findViewById<TextView>(R.id.tv_totalnilai)
        val btnDetail = view.findViewById<Button>(R.id.btn_detail)
        val btnSelesai = view.findViewById<Button>(R.id.btn_selesai)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)



        mainButton()
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun mainButton(){

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        riwayat = RiwayatActivity()
        holder.tvJudul.text = data[position].judul
        if (data[position].t_status!="2"){
            holder.btnSelesai.visibility = View.GONE
        }
        holder.tvStatus.text = data[position].k_status
        holder.tvHarga.text = Helper().gantiRp(data[position].harga)
        holder.tvTransport.text = Helper().gantiRp(data[position].transport)
        holder.tvTotal.text = Helper().gantiRp(data[position].ttl_harga)

        val gambar = "http://ws-tif.com/sedaya/admin/public/img/seni/"+data[position].gambar
        Picasso.get()
            .load(gambar)
            .placeholder(R.drawable.loadingseni)
            .error(R.drawable.loadingseni)
            .into(holder.imgProduk)
        //button bayar
        holder.btnDetail.setOnClickListener(){

            val activiti = Intent(activity, DetailTransaksiActivity::class.java)

            val string = Gson().toJson(data[position], History::class.java)
            activiti.putExtra("extra", string)
            activity.startActivity(activiti)
        }
        //button Selesai
        holder.btnSelesai.setOnClickListener(){
            ApiConfig.instanceRetrofit.selesai(
                data[position].no_transaksi
            )
                .enqueue(object : Callback<ResponModel>{
                    override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                        //handle ketika gagal
//                        Toast.makeText(this@AdapterRiwayat,"Error : "+t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                        val respon = response.body()!!
                        if (respon.code == 200) {
                            holder.btnSelesai.visibility = View.GONE
                            holder.tvStatus.text = "Selesai"
                        } else {
                        }
                    }
                })
        }
    }
}