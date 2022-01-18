package com.app.sedaya.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sedaya.MainActivity
import com.app.sedaya.app.ApiConfig
import com.app.sedaya.databinding.ActivityLupaPasswordBinding
import com.app.sedaya.databinding.ActivityUpdateProfileBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.ResponModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class LupaPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityLupaPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var buttonNavigationView : BottomNavigationView

    lateinit var sP: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLupaPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Update Password"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}