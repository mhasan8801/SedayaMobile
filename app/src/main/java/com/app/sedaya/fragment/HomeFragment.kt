package com.app.sedaya.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.app.sedaya.R
import com.app.sedaya.activity.RiwayatActivity
import com.app.sedaya.adapter.AdapterRekomSeni
import com.app.sedaya.adapter.AdapterSeni
import com.app.sedaya.adapter.AdapterSlider
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.FragmentHomeBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import com.app.sedaya.model.Seni
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var sP:SharedPref
    lateinit var vpSlider : ViewPager
    lateinit var rvSeni: RecyclerView
    lateinit var rvSeniRekom: RecyclerView
    lateinit var tvWelcome: TextView
    lateinit var tvNama: TextView
    lateinit var searchView: SearchView
    lateinit var swipeRefresh : SwipeRefreshLayout
    lateinit var layout_selamatdatang : MaterialCardView
    lateinit var layout_sliderseni : LinearLayout
    lateinit var btn_riwayat : ImageView
//    lateinit var rvProdukTerlasir: RecyclerView
//    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(layoutInflater)

        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        mainButton()

        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        btn_riwayat = view.findViewById(R.id.btn_riwayat)
        btn_riwayat.setOnClickListener {
            startActivity(Intent(requireContext(), RiwayatActivity::class.java))
        }

        sP = SharedPref(requireActivity())
        val user = sP.getUser()!!
        tvWelcome = view.findViewById(R.id.tv_welcome)
        tvNama = view.findViewById(R.id.tv_nama)
        if (sP.getStatusLogin()) {
            tvWelcome.setText("Selamat Datang ")
            tvNama.setText(user.nama)
        }


        init(view)
        getSeni()
        refresh()
        getRekomSeni()
        displaySeni()
        return view
    }

    private fun mainButton() {
//        btn_riwayat.setOnClickListener {
//            startActivity(Intent(requireContext(), RiwayatActivity::class.java))
//        }
    }
    private fun refresh() {
        swipeRefresh.setOnRefreshListener {
            getSeni()
            swipeRefresh.isRefreshing = false
        }
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

        rvSeniRekom.adapter = AdapterRekomSeni(requireActivity(), listRekomSeni)
        rvSeniRekom.layoutManager = layoutManager2

//        rvElektronik.adapter = AdapterSeni(requireActivity(), listSeni)
//        rvElektronik.layoutManager = layoutManager2

//        rvProdukTerlasir.adapter = AdapterSeni(requireActivity(), listSeni)
//        rvProdukTerlasir.layoutManager = layoutManager3
    }

    private var listRekomSeni: ArrayList<Seni> = ArrayList()
    fun getRekomSeni() {
        ApiConfig.instanceRetrofit.getRekomSeni().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.code == 200) {
                    listRekomSeni = res.seni
                    displaySeni()
                }
            }
        })
    }
    private var listSeni: ArrayList<Seni> = ArrayList()
    fun getSeni() {
        ApiConfig.instanceRetrofit.getSeniTerbaru().enqueue(object : Callback<ResponModel> {
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

//    private fun displaySearch() {
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query?.isEmpty() == true){
//                    layout_selamatdatang.visibility = View.VISIBLE
//                    layout_sliderseni.visibility = View.VISIBLE
//                    rvSeni.visibility = View.GONE
//                } else{
//                    pb.visibility = View.VISIBLE
//                    layout_banner.visibility = View.GONE
//                    layout_produk.visibility = View.GONE
//                    ApiConfig.instanceRetrofit.getSearch(query?.toLowerCase()!!).enqueue(object :  Callback<ResponModel>{
//                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                            pb.visibility = View.GONE
//                            val res = response.body()!!
//                            if(res.success == 1) {
//                                rv_listproduk.visibility = View.VISIBLE
//                                listProduk = res.produks
//                                displaylistProduk()
//                            } else if (res.produks == null){
//                                tvError.visibility = View.VISIBLE
//                                error(res.message)
//                            }
//                        }
//
//                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                            pb.visibility = View.GONE
//                            tvError.visibility = View.VISIBLE
//                            error(t.message.toString())
//                        }
//
//                    })
//                }
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText?.isEmpty() == true){
//                    layout_selamatdatang.visibility = View.VISIBLE
//                    layout_sliderseni.visibility = View.VISIBLE
//                    rv_listproduk.visibility = View.GONE
//
//                } else{
//                    pb.visibility = View.VISIBLE
//                    layout_banner.visibility = View.GONE
//                    layout_produk.visibility = View.GONE
//                    ApiConfig.instanceRetrofit.getSearch(newText?.toLowerCase()!!).enqueue(object :  Callback<ResponModel>{
//                        override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                            pb.visibility = View.GONE
//                            val res = response.body()!!
//                            if(res.success == 1) {
//                                rv_listproduk.visibility = View.VISIBLE
//                                listProduk = res.produks
//                                displaylistProduk()
//                            } else if (res.produks == null){
//                                tvError.visibility = View.VISIBLE
//                                error(res.message)
//                            }
//                        }
//
//                        override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                            pb.visibility = View.GONE
//                            tvError.visibility = View.VISIBLE
//                            error(t.message.toString())
//                        }
//
//                    })
//                }
//
//                return false
//            }
//
//        })
//    }

    fun init(view: View) {
        vpSlider = view.findViewById(R.id.vp_slider)
        rvSeni = view.findViewById(R.id.rv_seni)
        rvSeniRekom = view.findViewById(R.id.rv_senirekom)
        layout_selamatdatang = view.findViewById(R.id.layout_selamatdatang)
        layout_sliderseni = view.findViewById(R.id.layout_sliderseni)

//        rvElektronik = view.findViewById(R.id.rv_elektronik)
//        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
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