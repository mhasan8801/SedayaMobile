package com.app.sedaya.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.R
import com.app.sedaya.databinding.ActivityDetailSeniBinding
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.helper.Helper
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailSeniActivity : AppCompatActivity() {

    private var _binding: ActivityDetailSeniBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailSeniBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getInfo()

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
//            .resize(1000,600)
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