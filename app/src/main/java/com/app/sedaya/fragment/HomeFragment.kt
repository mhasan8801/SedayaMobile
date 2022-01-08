package com.app.sedaya.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.adapter.AdapterSeni
import com.app.sedaya.adapter.AdapterSlider
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.model.ResponModel
import com.app.sedaya.model.Seni
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var vpSlider : ViewPager
    lateinit var rvSeni: RecyclerView
    lateinit var rvProdukTerlasir: RecyclerView
    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getSeni()
        displaySeni()
        return view
    }

    fun displaySeni() {
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvSeni.adapter = AdapterSeni(requireActivity(), listSeni)
        rvSeni.layoutManager = layoutManager

        rvElektronik.adapter = AdapterSeni(requireActivity(), listSeni)
        rvElektronik.layoutManager = layoutManager2

        rvProdukTerlasir.adapter = AdapterSeni(requireActivity(), listSeni)
        rvProdukTerlasir.layoutManager = layoutManager3
    }

    private var listSeni: ArrayList<Seni> = ArrayList()
    fun getSeni() {
        ApiConfig.instanceRetrofit.getSeni().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.code == 200) {
                    listSeni = res.seni
                    displaySeni()
                }
            }
        })
    }

    fun init(view: View) {
        vpSlider = view.findViewById(R.id.vp_slider)
        rvSeni = view.findViewById(R.id.rv_seni)
        rvElektronik = view.findViewById(R.id.rv_elektronik)
        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
    }

//        val arrSeni: ArrayList<Seni>get(){
//        val arr = ArrayList<Seni>()
//        val s1 = Seni()
//        s1.judul = "Tari Saman"
//        s1.harga = "Rp.5.500.000"
//        s1.image = R.drawable.asset_produk1
//
//        val s2 = Seni()
//        s2.judul = "Reog Ponorogo"
//        s2.harga = "Rp.5.500.000"
//        s2.image = R.drawable.asset_produk2
//
//        val s3 = Seni()
//        s3.judul = "Hadroh Albanjari"
//        s3.harga = "Rp.5.500.000"
//        s3.image = R.drawable.asset_produk3
//
//        val s4 = Seni()
//        s4.judul = "Tari Kecak"
//        s4.harga = "Rp.5.500.000"
//        s4.image = R.drawable.asset_produk4
//
//        arr.add(s1)
//        arr.add(s2)
//        arr.add(s3)
//        arr.add(s4)
//
//        return arr
//    }
//
//    val arrElektronik: ArrayList<Seni>get(){
//        val arr = ArrayList<Seni>()
//        val s1 = Seni()
//        s1.judul = "Tari Saman"
//        s1.harga = "Rp.5.500.000"
//        s1.image = R.drawable.asset_produk1
//
//        val s2 = Seni()
//        s2.judul = "Reog Ponorogo"
//        s2.harga = "Rp.5.500.000"
//        s2.image = R.drawable.asset_produk2
//
//        val s3 = Seni()
//        s3.judul = "Hadroh Albanjari"
//        s3.harga = "Rp.5.500.000"
//        s3.image = R.drawable.asset_produk3
//
//        val s4 = Seni()
//        s4.judul = "Tari Kecak"
//        s4.harga = "Rp.5.500.000"
//        s4.image = R.drawable.asset_produk4
//
//        arr.add(s1)
//        arr.add(s2)
//        arr.add(s3)
//        arr.add(s4)
//
//        return arr
//    }
//
//    val arrProdukTerlaris: ArrayList<Seni>get(){
//        val arr = ArrayList<Seni>()
//        val s1 = Seni()
//        s1.judul = "Tari Saman"
//        s1.harga = "Rp.5.500.000"
//        s1.image = R.drawable.asset_produk1
//
//        val s2 = Seni()
//        s2.judul = "Reog Ponorogo"
//        s2.harga = "Rp.5.500.000"
//        s2.image = R.drawable.asset_produk2
//
//        val s3 = Seni()
//        s3.judul = "Hadroh Albanjari"
//        s3.harga = "Rp.5.500.000"
//        s3.image = R.drawable.asset_produk3
//
//        val s4 = Seni()
//        s4.judul = "Tari Kecak"
//        s4.harga = "Rp.5.500.000"
//        s4.image = R.drawable.asset_produk4
//
//        arr.add(s1)
//        arr.add(s2)
//        arr.add(s3)
//        arr.add(s4)
//
//        return arr
//    }
}