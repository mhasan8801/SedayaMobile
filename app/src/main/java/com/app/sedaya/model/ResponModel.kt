package com.app.sedaya.model

class ResponModel {
    var code = 404
    lateinit var message:String
    var data = User()
    var seni: ArrayList<Seni> = ArrayList()
    var history: ArrayList<History> = ArrayList()
}