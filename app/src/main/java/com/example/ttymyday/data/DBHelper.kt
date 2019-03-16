package com.example.ttymyday.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import java.lang.AssertionError

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        if (db!=null){
            db.execSQL(STRUCT_NAME_VALUE);
            db.execSQL(STRUCT_SCHEDULE);
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        for (i:Int in oldVersion..newVersion){
            //TODO("update the upgrade")
        }
    }

    /***
     * @author cht
     * @param name 查询的键
     * @param default 未找到该键时，返回的默认值
     */
    fun getValue(name:String,default:String):String{
        val db:SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery("select * from $NAME_NAME_VALUE where name=?", arrayOf(name))
        var v:String = ""
        if (cursor.moveToFirst() && cursor.count > 0){
            v = cursor.getString(cursor.getColumnIndex("name"))
        } else{
            v = default
        }
        cursor.close();
        return v
    }

    fun setValue(name:String,value:String){
        val readdb:SQLiteDatabase = this.readableDatabase
        val writedb:SQLiteDatabase = this.writableDatabase
        val cursor = readdb.rawQuery("select * from $NAME_NAME_VALUE where name=?", arrayOf(name))
        if (cursor.count > 0){
            writedb.execSQL("update $NAME_NAME_VALUE set value=? where name=?", arrayOf(value,name))
        } else{
            writedb.execSQL("insert into $NAME_NAME_VALUE (name,value) values (?,?)", arrayOf(name,value))
        }
        cursor.close()
    }

    //var user:User = User()
    //get(){
    //    var database:SQLiteDatabase = this.writableDatabase
    //    var isExist = false
    //}

    companion object {
        private const val DB_NAME = "myday.db"
        private const val DB_VERSION=1

        private const val NAME_NAME_VALUE = "name_value"
        private const val NAME_SCHEDULE ="schedule"

        // 键值对数据
        private const val STRUCT_NAME_VALUE:String = "create table $NAME_NAME_VALUE(id integer primary key autoincrement,name text,value text)";
        // 日程数据
        private const val STRUCT_SCHEDULE:String = "create table $NAME_SCHEDULE(id integer primary key autoincrement,username text,start_time text,end_time text,alarm text,repeat text,title text,location text,remark text,tag text,steps text)"

        private var _instance:DBHelper? = null
        fun getInstance(context:Context):DBHelper {
            if (_instance == null)
                _instance = DBHelper(context);

            return _instance ?:throw AssertionError("An error occured!!!")
        }
    }
}



