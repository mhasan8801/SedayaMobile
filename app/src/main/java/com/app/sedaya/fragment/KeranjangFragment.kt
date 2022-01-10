package com.app.sedaya.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.app.sedaya.R
import com.app.sedaya.adapter.AdapterSeni
import com.app.sedaya.adapter.AdapterSeniSemua
import com.app.sedaya.adapter.AdapterSlider
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.model.ResponModel
import com.app.sedaya.model.Seni
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KeranjangFragment : Fragment() {

//    lateinit var vpSlider : ViewPager
    lateinit var rvSeni: RecyclerView
//    lateinit var rvProdukTerlasir: RecyclerView
//    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        getSeni()
        displaySeni()
        return view
    }

    fun displaySeni() {

//        val layoutManager = LinearLayoutManager(activity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvSeni.adapter = AdapterSeniSemua(requireActivity(), listSeni)
//        rvSeni.layoutManager = layoutManager

//        rvElektronik.adapter = AdapterSeni(requireActivity(), listSeni)
//        rvElektronik.layoutManager = layoutManager2

//        rvProdukTerlasir.adapter = AdapterSeni(requireActivity(), listSeni)
//        rvProdukTerlasir.layoutManager = layoutManager3
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
        rvSeni = view.findViewById(R.id.rv_senisemua)
//        rvElektronik = view.findViewById(R.id.rv_elektronik)
//        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
    }
}