package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.sedaya.R
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityDetailSeniBinding
import com.app.sedaya.databinding.ActivityDetailTransaksiBinding
import com.app.sedaya.helper.Helper
import com.app.sedaya.model.History
import com.app.sedaya.model.ResponModel
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaksiActivity : AppCompatActivity() {


    private var _binding: ActivityDetailTransaksiBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Detail Pesanan"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getInfo()


    }
    fun toastSuccess(message:String){
        Toast.makeText(this@DetailTransaksiActivity,message, Toast.LENGTH_SHORT).show()
    }
    fun getInfo(){
        val data = intent.getStringExtra("extra")
        val history = Gson().fromJson<History>(data, History::class.java)

        if (history.t_status>"1"){
            binding.btnBayar.visibility = View.GONE
        }
        binding.btnBayar.setOnClickListener{
            if (history.t_status=="1"){
                //button batal
                ApiConfig.instanceRetrofit.pembayaran(
                    history.no_transaksi
                )
                    .enqueue(object : Callback<ResponModel> {
                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                            //handle ketika gagal
                            //                        Toast.makeText(this@AdapterRiwayat,"Error : "+t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                            val respon = response.body()!!
                            if (respon.code == 200) {
                                val intent = Intent(this@DetailTransaksiActivity, RiwayatActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this@DetailTransaksiActivity,respon.message, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@DetailTransaksiActivity,respon.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }else if(history.t_status=="0"){

                Toast.makeText(this@DetailTransaksiActivity,"Tunggu Konfirmasi dari Seniman!", Toast.LENGTH_SHORT).show()
            }
        }
        if (history.t_status>="2"){
            binding.btnBatal.visibility = View.GONE
        }
        binding.btnBatal.setOnClickListener{
            if (history.t_status<"2"){
                //button batal
                ApiConfig.instanceRetrofit.batal(
                    history.no_transaksi
                )
                    .enqueue(object : Callback<ResponModel> {
                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                            //handle ketika gagal
                            Toast.makeText(this@DetailTransaksiActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                            val respon = response.body()!!
                            if (respon.code == 200) {
                                val intent = Intent(this@DetailTransaksiActivity, RiwayatActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this@DetailTransaksiActivity,respon.message, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@DetailTransaksiActivity,respon.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
        }

        //set value
        binding.tvNoTransaksi.text = history.no_transaksi.toString()
        binding.tvStatus.text = history.k_status
        binding.tvTglpesan.text = history.tgl_pemesanan
        binding.tvTglkegiatan.text = history.tgl_kegiatan
        binding.tvAlamat.text = history.alamat
        binding.tvHarga.text = Helper().gantiRp(history.harga)
        binding.tvJudul.text = history.judul
        binding.tvTransport.text = Helper().gantiRp(history.transport)
        binding.tvKategori.text ="Kategori : "+history.kategori
        binding.tvTotal.text = Helper().gantiRp(history.ttl_harga)

        val gambar = "http://ws-tif.com/sedaya/admin/public/img/seni/"+history.gambar
        Picasso.get()
            .load(gambar)
            .placeholder(R.drawable.loadingseni)
            .error(R.drawable.loadingseni)
            .into(binding.imgProduk)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}