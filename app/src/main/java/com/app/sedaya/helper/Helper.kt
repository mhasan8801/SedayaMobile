package com.app.sedaya.helper

import java.text.NumberFormat
import java.util.*

class Helper {

    fun gantiRp(string: String) :String{
        return NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(string))
    }

}