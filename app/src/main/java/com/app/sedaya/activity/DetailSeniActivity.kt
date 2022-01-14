package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityDetailSeniBinding
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.helper.Helper
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSeniActivity : AppCompatActivity() {

    private var _binding: ActivityDetailSeniBinding? = null
    private val binding get() = _binding!!
    lateinit var sP: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailSeniBinding.inflate(layoutInflater)

        sP = SharedPref(this)
        setContentView(binding.root)
        mainButton()
        getInfo()

    }
    fun mainButton(){
        binding.btnPesan.setOnClickListener{
            transaksi()
        }
    }

    fun transaksi() {

        val data = intent.getStringExtra("extra")
        val seni = Gson().fromJson<Seni>(data, Seni::class.java)
        val user = sP.getUser()!!
        if (binding.edtTanggal.text?.isEmpty()!!) {
            binding.edtTanggal.error="Silahkan masukan tanggal kegiatan"
            binding.edtTanggal.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.transaksi(
            user.usr_id,
            seni.sn_id.toString(),
            binding.edtTanggal.text.toString(),
            seni.harga.toString()
        )
            .enqueue(object : Callback<ResponModel> {
                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                    //handle ketika gagal
                    Toast.makeText(this@DetailSeniActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                    val respon = response.body()!!
                    if (respon.code == 200) {
                        sP.setStatusLogin(true)
                        val intent = Intent(this@DetailSeniActivity, RiwayatActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@DetailSeniActivity,respon.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailSeniActivity,respon.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        val seni = Gson().fromJson<Seni>(data, Seni::class.java)

        //set value
        binding.tvNama.text = seni.judul
        binding.tvHarga.text = Helper().gantiRp(seni.harga)
        binding.tvKeterangan.text = seni.keterangan
        binding.tvAlamat.text = seni.alamat
        binding.tvNamaseniman.text = seni.nama_snm
        binding.tvKategori.text = seni.kategori
        binding.tvJenis.text = seni.jenis

        val gambar = "http://ws-tif.com/sedaya/admin/public/img/seni/"+seni.gambar
        Picasso.get()
            .load(gambar)
            .placeholder(R.drawable.loadingseni)
            .error(R.drawable.loadingseni)
//            .resize(400,400)
            .into(binding.image)

        //Set toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = seni.judul
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}