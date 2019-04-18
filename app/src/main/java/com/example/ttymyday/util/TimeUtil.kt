package com.example.ttymyday.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil{
    val now:Date
    get() {
        return Date()
    }

    fun toDateString(date:Date):String{
        val formatter:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
        return formatter.format(date)
    }

    fun toDateTimeString(date:Date):String{
        val formatter:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINESE)
        return formatter.format(date)
    }

    private fun toDateVar(dateString:String,format:String):Date{
        val formatter:SimpleDateFormat = SimpleDateFormat(format,Locale.CHINESE)
        return formatter.parse(dateString)
    }

    fun toDate(dateString: String):Date {
        return toDateVar(dateString,"yyyy-MM-dd")
    }

    fun toDateTime(dateString: String):Date{
        return toDateVar(dateString,"yyyy-MM-dd HH:mm:ss")
    }

}