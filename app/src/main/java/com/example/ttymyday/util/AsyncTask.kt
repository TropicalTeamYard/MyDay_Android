package com.example.ttymyday.util

import android.os.Handler

object AsyncTask{

    fun get(url:String,callBack: (String?)->Unit){
        val handler:Handler = Handler();
        Thread( run{ ->{
            val response:String? = NetUtil.get(url)
            handler.post(run{->{ callBack(response) }})
            Unit
        } })
    }

    fun post(url:String,content:String,callBack:(String?)-> Unit){
        val handler:Handler = Handler();
        Thread( run { ->{
            val responce:String? = NetUtil.post(url,content)
            handler.post(run { ->{callBack(responce)} })
            Unit
        } }).start()
    }
}