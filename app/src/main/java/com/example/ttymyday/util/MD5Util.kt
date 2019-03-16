package com.example.ttymyday.util

import java.lang.Exception
import java.security.MessageDigest

object MD5Util{
    fun getMd5(input:String):String{
        //数据加密算法，即拿到一个MD5转换器
        val messageDigest:MessageDigest = MessageDigest.getInstance("MD5")
        val inputByteArray:ByteArray = input.toByteArray()
        messageDigest.update(inputByteArray)
        //转换并返回结果，也是字节数组，包含16个元素
        val resultByteArray:ByteArray = messageDigest.digest()
        return byteArrayToHex(resultByteArray)
    }

    fun byteArrayToHex(byteArray: ByteArray):String{
        val hexDigits:CharArray= charArrayOf('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F')
        val resultCharArray:CharArray = CharArray(byteArray.size * 2)
        var index = 0
        for (b:Byte in byteArray){
            resultCharArray[index++]= hexDigits[b.toInt().shr(4) and 0xf]
            resultCharArray[index++] = hexDigits[b.toInt() and 0xf]
        }

        return String(resultCharArray)
    }
}