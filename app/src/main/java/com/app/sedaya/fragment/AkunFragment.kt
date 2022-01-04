package com.app.sedaya.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.app.sedaya.R
import com.app.sedaya.databinding.FragmentAkunBinding
import com.app.sedaya.helper.SharedPref


class AkunFragment : Fragment() {

    lateinit var sP:SharedPref
    lateinit var btnLogout:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_akun, container, false)
        btnLogout = view.findViewById(R.id.btn_logout)

        sP = SharedPref(activity!!)

        btnLogout.setOnClickListener {
            sP.setStatusLogin(false)
        }

        return view
    }
}