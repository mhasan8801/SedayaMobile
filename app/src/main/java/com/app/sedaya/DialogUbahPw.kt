package com.app.sedaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class DialogUbahPw: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.ubah_pw_dialog, container, false)

        return rootView
    }

    fun show(supportFragmentManager: Nothing?, s: String) {
        
    }
}