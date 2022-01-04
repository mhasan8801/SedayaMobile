package com.app.sedaya.model

import java.io.Serializable

class Seni : Serializable{
    lateinit var  sn_id: String
    lateinit var snm_id: String
    lateinit var nama_snm: String
    lateinit var alamat: String
    lateinit var judul: String
    lateinit var kategori: String
    lateinit var jenis: String
    lateinit var keterangan: String
    lateinit var harga: String
    var image: Int=0
    lateinit var status: String
}