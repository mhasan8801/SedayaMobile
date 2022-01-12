package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityMasukBinding
import com.app.sedaya.databinding.ActivityRegisterBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    lateinit var sP: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sP = SharedPref(this)

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.btnKliksini.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }
    fun register() {
        if (binding.edtNama.text.isEmpty()) {
            binding.edtNama.error="Kolom nama tidak boleh kosong"
            binding.edtNama.requestFocus()
            return
        } else if (binding.edtEmail.text.isEmpty()) {
            binding.edtEmail.error="Kolom email tidak boleh kosong"
            binding.edtEmail.requestFocus()
            return
        } else if (binding.edtTelp.text.isEmpty()) {
            binding.edtTelp.error="Kolom no.handphone tidak boleh kosong"
            binding.edtTelp.requestFocus()
            return
        } else if (binding.edtAlamat.text.isEmpty()) {
            binding.edtAlamat.error="Kolom alamat tidak boleh kosong"
            binding.edtAlamat.requestFocus()
            return
        } else if (binding.edtPassword.text.isEmpty()) {
            binding.edtPassword.error = "Kolom password tidak boleh kosong"
            binding.edtPassword.requestFocus()
            return
        } else if (isValidMail(binding.edtEmail.text.toString())==false){
            binding.edtEmail.error="Gunakan format email yang benar"
            binding.edtEmail.requestFocus()
            return
        }else if(isValidMobile(binding.edtTelp.text.toString())==false){
            binding.edtTelp.error="No.handphone harus angka"
            binding.edtTelp.requestFocus()
            return
        }

        binding.pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(
            binding.edtNama.text.toString(),
            binding.edtEmail.text.toString(),
            binding.edtTelp.text.toString(),
            binding.edtAlamat.text.toString(),
            binding.edtPassword.text.toString()
        ).enqueue(object : Callback<ResponModel>{
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                binding.pb.visibility = View.GONE
                //handle ketika gagal
                Toast.makeText(this@RegisterActivity,"Error : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                binding.pb.visibility = View.GONE
                val respon = response.body()!!
                if (respon.code == 200) {
                    sP.setStatusLogin(true)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity,"Selamat datang "+respon.data.nama, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity,"Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}