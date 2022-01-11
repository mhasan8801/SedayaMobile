package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.adapter.AdapterRiwayat
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.databinding.ActivityRiwayatBinding
import com.app.sedaya.databinding.FragmentHomeBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.History
import com.app.sedaya.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {


    lateinit var sP: SharedPref
    lateinit var rvHistory: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        sP = SharedPref(this)
        rvHistory = findViewById(R.id.rv_riwayat)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        refresh()
        getHistory()
    }

    private fun refresh() {
        swipeRefresh.setOnRefreshListener {
            getHistory()
            swipeRefresh.isRefreshing = false
        }
    }
    fun displayHistory() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        rvHistory.adapter = AdapterRiwayat(this, listHistory)
        rvHistory.layoutManager = layoutManager

    }
    private var listHistory: ArrayList<History> = ArrayList()
    fun getHistory() {
        val user = sP.getUser()!!
        ApiConfig.instanceRetrofit.history(user.usr_id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.code == 200) {
                    listHistory = res.history
                    displayHistory()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
//    fun init(view: View) {
//        rvHistory = view.findViewById(R.id.rv_riwayat)
//        btnBatal = view.findViewById(R.id.btn_batal)
//        btnBayar = view.findViewById(R.id.btn_bayar)
//        btnSelesai = view.findViewById(R.id.btn_selesai)

//        rvElektronik = view.findViewById(R.id.rv_elektronik)
//        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
//    }
}