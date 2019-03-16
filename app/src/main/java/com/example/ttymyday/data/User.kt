package com.example.ttymyday.data

class User(){
    constructor(usertype:String,username:String,nickname:String,token:String):this()
    {
        this.usertype = usertype;
        this.username = username;
        this.nickname = nickname;
        this.token = token;
    }

    /**
     * 用户类型，分为三种#NONE,#LOCAL,#CLOUD
     */
    lateinit var usertype:String
    lateinit var username:String
    lateinit var nickname:String
    lateinit var token:String
}