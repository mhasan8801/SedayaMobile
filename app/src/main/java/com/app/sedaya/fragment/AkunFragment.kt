package com.app.sedaya.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.app.sedaya.MainActivity
import com.app.sedaya.R
import com.app.sedaya.activity.LoginActivity
import com.app.sedaya.databinding.FragmentAkunBinding
import com.app.sedaya.helper.SharedPref


class AkunFragment : Fragment() {

    lateinit var sP:SharedPref
    lateinit var btnLogout:TextView
    lateinit var tvNama:TextView
    lateinit var tvEmail:TextView
    lateinit var tvTelp:TextView
    lateinit var tvAlamat:TextView
    lateinit var tvInisial:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        sP = SharedPref(requireActivity())

        btnLogout.setOnClickListener {
            sP.setStatusLogin(false)
        }

        setData()
        return view
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
        tvNama = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        tvTelp = view.findViewById(R.id.tv_phone)
        tvAlamat = view.findViewById(R.id.tv_alamat)
        tvInisial = view.findViewById(R.id.tv_inisial)
    }
}