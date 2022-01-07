package com.app.sedaya.model

class ResponModel {
    var success = 0
    var code = 404
    lateinit var message:String
    var data = User()
    var seni: ArrayList<Seni> = ArrayList()
}