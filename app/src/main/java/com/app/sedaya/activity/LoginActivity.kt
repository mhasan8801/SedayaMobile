package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.DialogLupaPw
import com.app.sedaya.MainActivity
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.databinding.ActivityRegisterBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var sP:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sP = SharedPref(this)

        binding.btnMasuk.setOnClickListener {
            login()
        }

        binding.btnDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.tvLupaPw.setOnClickListener {
            var dialog = DialogLupaPw()

            dialog.show(supportFragmentManager, "dialogCustom")
        }

    }

    fun login() {
        if (binding.edtUsername.text.isEmpty()) {
            binding.edtUsername.error="Kolom email tidak boleh kosong"
            binding.edtUsername.requestFocus()
            return
        } else if (binding.edtPassword.text.isEmpty()) {
            binding.edtPassword.error = "Kolom password tidak boleh kosong"
            binding.edtPassword.requestFocus()
            return
        }

        binding.pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(
            binding.edtUsername.text.toString(),
            binding.edtPassword.text.toString()
        ).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                binding.pb.visibility = View.GONE
                //handle ketika gagal
                Toast.makeText(this@LoginActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                binding.pb.visibility = View.GONE
                val respon = response.body()!!
                if (respon.code == 200) {
                    sP.setStatusLogin(true)
                    sP.setUser(respon.data)
//                    sP.setString(sP.nama, respon.data.nama)
//                    sP.setString(sP.email, respon.data.email)
//                    sP.setString(sP.telp, respon.data.telp)
//                    sP.setString(sP.alamat, respon.data.alamat)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity,"Selamat datang "+respon.data.nama, Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this@LoginActivity,"Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}