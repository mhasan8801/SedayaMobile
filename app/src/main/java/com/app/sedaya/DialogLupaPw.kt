package com.app.sedaya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.app.sedaya.activity.LupaPasswordActivity
import com.app.sedaya.activity.RegisterActivity

class DialogLupaPw: DialogFragment() {

    lateinit var btnCancel: TextView
    lateinit var btnUbah: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.lupa_pw_dialog, container, false)

        init(view)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnUbah.setOnClickListener {
            startActivity(Intent(requireContext(), LupaPasswordActivity::class.java))
        }

        return view
    }

    private fun init(view: View) {
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnUbah = view.findViewById(R.id.btn_ubah)
    }
}