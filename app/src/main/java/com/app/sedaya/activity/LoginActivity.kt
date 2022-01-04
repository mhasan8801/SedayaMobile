package com.app.sedaya.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.R
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.helper.SharedPref

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var sP:SharedPref
//    val btn_prosesLogin = findViewById(R.id.btn_prosesLogin) as Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sP = SharedPref(this)

        binding.btnProsesLogin.setOnClickListener {
            sP.setStatusLogin(true)
        }
    }

}