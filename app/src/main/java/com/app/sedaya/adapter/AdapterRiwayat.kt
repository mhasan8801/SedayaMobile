package com.app.sedaya.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.sedaya.R
import com.app.sedaya.activity.DetailSeniActivity
import com.app.sedaya.helper.Helper
import com.app.sedaya.model.History
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterRiwayat(var activity: Activity, var data: ArrayList<History>) : RecyclerView.Adapter<AdapterRiwayat.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul = view.findViewById<TextView>(R.id.tv_judul)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harganilai)
        val tvTransport = view.findViewById<TextView>(R.id.tv_transportnilai)
        val tvTotal = view.findViewById<TextView>(R.id.tv_totalnilai)
        val btnBatal = view.findViewById<Button>(R.id.btn_batal)
        val btnBayar = view.findViewById<Button>(R.id.btn_bayar)
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
    fun Batal() {
        //
//        val user = sP.getUser()!!
//
//        ApiConfig.instanceRetrofit.transaksi(
//            user.usr_id
//        )
//            .enqueue(object : Callback<ResponModel>{
//                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                    //handle ketika gagal
//                    Toast.makeText(this@RiwayatActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                    val respon = response.body()!!
//                    if (respon.code == 200) {
//                        sP.setStatusLogin(true)
//                        val intent = Intent(this@RiwayatActivity, RiwayatActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                        finish()
//                        Toast.makeText(this@RiwayatActivity,"Selamat datang "+respon.data.nama, Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this@RiwayatActivity,"Error : "+respon.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tvJudul.text = data[position].judul
        if (data[position].t_status=="0"){
            holder.btnBayar.visibility = View.GONE
            holder.btnSelesai.visibility = View.GONE
        }else if (data[position].t_status=="1"){
            holder.btnSelesai.visibility = View.GONE
        }else if (data[position].t_status=="2"){
            holder.btnBayar.visibility = View.GONE
            holder.btnBatal.visibility = View.GONE
        }else if (data[position].t_status=="3"){
            holder.btnBatal.visibility = View.GONE
            holder.btnBayar.visibility = View.GONE
            holder.btnSelesai.visibility = View.GONE
        }else if (data[position].t_status=="4"){
            holder.btnBatal.visibility = View.GONE
            holder.btnBayar.visibility = View.GONE
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

//        holder.layout.setOnClickListener {
//            val activiti = Intent(activity, DetailSeniActivity::class.java)
//
//            val string = Gson().toJson(data[position], Seni::class.java)
//            activiti.putExtra("extra", string)
//            activity.startActivity(activiti)
//        }
    }
}