package com.app.sedaya.model

class ResponModel {
    var success = 0
    var code = 200
    lateinit var message:String
    var data = User()
    var seni: ArrayList<Seni> = ArrayList()
}