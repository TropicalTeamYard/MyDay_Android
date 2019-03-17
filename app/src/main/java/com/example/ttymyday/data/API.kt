package com.example.ttymyday.data

object API{
    fun getDomain():String{
        if (MODE == "DEBUG"){
            //return "http://101.132.122.143:8080/board"
            return "http://10.128.38.22:80/"
        } else {
            return ""
        }
    }

    fun getRoute(region:RegionParam):String{
        //when-case expression
        val r:String = when(region){
            RegionParam.USER -> "api/user"
            RegionParam.TIME -> "api/time"
            RegionParam.GETINFO -> "api/getinfo"
            RegionParam.COURSE -> "api/course"
            RegionParam.SETINFO -> "api/setinfo"
            RegionParam.MSGBOARD-> "api/msgboard"
            RegionParam.SHARED -> "api/shared"
        }

        return getDomain() + r
    }

    private var MODE:String = "DEBUG";
}