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
import com.app.sedaya.databinding.ActivityUpdateProfileBinding
import com.app.sedaya.databinding.FragmentHomeBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.History
import com.app.sedaya.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {

    private var _binding: ActivityRiwayatBinding? = null
    private val binding get() = _binding!!

    lateinit var sP: SharedPref
    lateinit var rvHistory: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRiwayatBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Riwayat Pesanan"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        sP = SharedPref(this)
        rvHistory = findViewById(R.id.rv_riwayat)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        refresh()
        getHistory()
    }

//    fun Batal() {
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
//    }

    fun refresh() {
        swipeRefresh.setOnRefreshListener {
            getHistory()
            toastSuccess("Data sudah terbaru")
            swipeRefresh.isRefreshing = false
        }
    }

    fun toastError(message:String){
        Toast.makeText(this@RiwayatActivity, message, Toast.LENGTH_SHORT).show()
    }
    fun toastSuccess(message:String){
        Toast.makeText(this@RiwayatActivity,message, Toast.LENGTH_SHORT).show()
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
}