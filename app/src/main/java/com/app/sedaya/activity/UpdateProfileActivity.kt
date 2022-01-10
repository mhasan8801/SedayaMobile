package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityMasukBinding
import com.app.sedaya.databinding.ActivityRegisterBinding
import com.app.sedaya.databinding.ActivityUpdateProfileBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    private var _binding: ActivityUpdateProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var buttonNavigationView : BottomNavigationView

    lateinit var sP: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Update Akun"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sP = SharedPref(this)

        mainButton()
        setData()
    }

    private fun setData() {
        val user = sP.getUser()!!
        binding.edtNama.setText(user.nama)
        binding.edtTelp.setText(user.telp)
        binding.edtAlamat.setText(user.alamat)
    }

    private fun mainButton(){
        binding.btnSimpan.setOnClickListener {
            updateProfile()
        }
    }

    fun updateProfile() {

        val user = sP.getUser()!!
        if (binding.edtNama.text?.isEmpty()!!) {
            binding.edtNama.error="Kolom nama tidak boleh kosong"
            binding.edtNama.requestFocus()
            return
        }
        if (binding.edtTelp.text?.isEmpty()!!) {
            binding.edtTelp.error="Kolom no.handphone tidak boleh kosong"
            binding.edtTelp.requestFocus()
            return
        }
        if (binding.edtAlamat.text?.isEmpty()!!) {
            binding.edtAlamat.error="Kolom alamat tidak boleh kosong"
            binding.edtAlamat.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.updateProfile(
            user.usr_id,
            binding.edtNama.text.toString(),
            binding.edtTelp.text.toString(),
            binding.edtAlamat.text.toString(),
        )
            .enqueue(object : Callback<ResponModel>{
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal
                Toast.makeText(this@UpdateProfileActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                if (respon.code == 200) {
                    sP.setStatusLogin(true)
                    val intent = Intent(this@UpdateProfileActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@UpdateProfileActivity,"Selamat datang "+respon.data.nama, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UpdateProfileActivity,"Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}