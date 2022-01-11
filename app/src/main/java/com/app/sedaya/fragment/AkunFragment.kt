package com.app.sedaya.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.activity.DetailSeniActivity
import com.app.sedaya.activity.LoginActivity
import com.app.sedaya.activity.UpdateProfileActivity
import com.app.sedaya.databinding.ActivityLoginBinding
import com.app.sedaya.databinding.FragmentAkunBinding
import com.app.sedaya.helper.SharedPref
import com.app.sedaya.model.Seni
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.Main


class AkunFragment : Fragment() {

    lateinit var sP:SharedPref
    lateinit var btnLogout:TextView
    lateinit var btnUpdate:TextView
    lateinit var tvNama:TextView
    lateinit var tvEmail:TextView
    lateinit var tvTelp:TextView
    lateinit var tvAlamat:TextView
    lateinit var swipeRefresh : SwipeRefreshLayout
    lateinit var tvInisial:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_akun, container, false)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        init(view)

        sP = SharedPref(requireActivity())
        refresh()
        mainButton()
        setData()
        return view
    }
    private fun refresh() {
        swipeRefresh.setOnRefreshListener {
            setData()
            swipeRefresh.isRefreshing = false
        }
    }
    private fun mainButton() {
        btnLogout.setOnClickListener {
            sP.setStatusLogin(false)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        btnUpdate.setOnClickListener {
            startActivity(Intent(requireContext(), UpdateProfileActivity::class.java))
        }

    }

    fun String?.getInitial(): String {
        if (this.isNullOrEmpty()) return ""
        val array = this.split(" ")
        if (array.isEmpty()) return this
        var inisial = array[0].substring(0, 1)
        if (array.size > 1) inisial += array[1].substring(0, 1)
        return inisial.uppercase()
    }

    fun setData() {
        if (sP.getUser() == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }
        val user = sP.getUser()!!
        tvNama.text = user.nama
        tvEmail.text = user.email
        tvTelp.text = user.telp
        tvAlamat.text = user.alamat
        tvInisial.text = user.nama.getInitial()
    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        btnUpdate = view.findViewById(R.id.btn_update)
        tvNama = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        tvTelp = view.findViewById(R.id.tv_phone)
        tvAlamat = view.findViewById(R.id.tv_alamat)
        tvInisial = view.findViewById(R.id.tv_inisial)
    }
}