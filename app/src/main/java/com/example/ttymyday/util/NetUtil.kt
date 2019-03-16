

package com.example.ttymyday.util

import android.accounts.NetworkErrorException
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object NetUtil{
    fun post(url:String,content:String):String?{
        var conn:HttpURLConnection? = null;
        try {
            val murl = URL(url)
            conn = murl.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.readTimeout = 5000
            conn.connectTimeout = 10000
            conn.doOutput = true

            val data:String = content
            val out:OutputStream = conn.outputStream
            out.write(data.toByteArray())
            out.flush()
            out.close()

            val responseCode:Int = conn.responseCode
            if (responseCode == 200){
                val iStream:InputStream = conn.inputStream
                return getStringFromInputStream(iStream)
            } else  {
                throw NetworkErrorException("response status is $responseCode")
            }

        } catch (e:Exception){
            e.printStackTrace()
        } finally {
            if (conn!=null){
                conn.disconnect()
            }
        }

        return null
    }

    fun get(url:String):String?{
        var conn:HttpURLConnection? = null
        try {
            val murl:URL = URL(url)
            conn = murl.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.readTimeout = 5000
            conn.connectTimeout = 10000

            val responseCode:Int = conn.responseCode
            if (responseCode == 200){
                val istream:InputStream = conn.inputStream
                return getStringFromInputStream(istream)
            } else {
                throw NetworkErrorException("response status is $responseCode")
            }
        } catch (e:Exception){
            e.printStackTrace()
        } finally {
            if (conn != null){
                conn.disconnect()
            }
        }

        return null
    }
    private fun getStringFromInputStream(istream:InputStream):String{
        val os :ByteArrayOutputStream = ByteArrayOutputStream()
        val buffer:ByteArray = ByteArray(1024)
        var len:Int
        do {
            len = istream.read(buffer)
            if (len == -1){
                break
            }
            os.write(buffer,0,len)
        } while (true)
        istream.close()
        val state:String = os.toString()
        os.close()
        return state
    }
}