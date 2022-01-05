package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.R
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.databinding.ActivityMasukBinding
import com.app.sedaya.helper.SharedPref

class MasukActivity : AppCompatActivity() {

    private var _binding: ActivityMasukBinding? = null
    private val binding get() = _binding!!

    lateinit var sP:SharedPref
//    val btn_prosesLogin = findViewById(R.id.btn_prosesLogin) as Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMasukBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sP = SharedPref(this)

        mainButton()
    }

    private fun mainButton() {
        binding.btnProsesLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}