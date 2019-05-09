package com.example.ttymyday.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.ttymyday.model.ScheduleTag
import java.lang.AssertionError

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(STRUCT_NAME_VALUE)
        db?.execSQL(STRUCT_SCHEDULE)
        db?.execSQL(STRUCT_SCHEDULE_TAG)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        for (i:Int in oldVersion..newVersion){
            //TODO("update the upgrade")
        }
    }

    //region 设置键值对读写

    /***
     * @author cht
     * @param name 查询的键
     * @param default 未找到该键时，返回的默认值
     */
    fun getSettingsValue(name:String, default:String):String{
        val db:SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery("select * from $NAME_NAME_VALUE where name=?", arrayOf(name))
        var v:String = ""
        if (cursor.moveToFirst() && cursor.count > 0){
            //BUG 找到了一处非常奇怪的bug，导致读取错误的设置信息
            v = cursor.getString(cursor.getColumnIndex("value"))
        } else{
            v = default
        }

        try {
            cursor.close()
            db.close()
        }catch (e:SQLiteException){
            e.printStackTrace()
        }

        return v
    }

    fun setSettingsValue(name:String, value:String){
        val readdb:SQLiteDatabase = this.readableDatabase
        val writedb:SQLiteDatabase = this.writableDatabase
        val cursor = readdb.rawQuery("select * from $NAME_NAME_VALUE where name=?", arrayOf(name))
        val contentValues = ContentValues()
        contentValues.put("value",value)
        if (cursor.count > 0){
            writedb.update(NAME_NAME_VALUE,contentValues,"name = ?", arrayOf(name))
            //writedb.execSQL("update $NAME_NAME_VALUE set value=? where name=?", arrayOf(value,name))
        } else{
            contentValues.put("name",name)
            writedb.insert(NAME_NAME_VALUE,null,contentValues)
            //writedb.execSQL("insert into $NAME_NAME_VALUE (name,value) values (?,?)", arrayOf(name,value))
        }

        try {
            cursor.close()
            readdb.close()
            writedb.close()
        } catch (e:SQLiteException){
            e.printStackTrace()
        }
    }

    //var user:User = User()
    //get(){
    //    var database:SQLiteDatabase = this.writableDatabase
    //    var isExist = false
    //}

    //endregion

    //region 日程标签
    fun setScheduleTagValue(scheduleTag: ScheduleTag){
        val db:SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        if (scheduleTag.name != null){
            contentValues.put("name",scheduleTag.name)
        }
        if (scheduleTag.title != null){
            contentValues.put("title",scheduleTag.title)
        }
        if (scheduleTag.owner != null){
            contentValues.put("owner",scheduleTag.owner)
        }
        if (scheduleTag.icon != null){
            contentValues.put("icon",scheduleTag.icon)
        }
        if (scheduleTag.users != null){
            contentValues.put("users",scheduleTag.users)
        }
        if (scheduleTag.create_time != null){
            contentValues.put("create_time",scheduleTag.create_time)
        }
        if (scheduleTag.update_time != null){
            contentValues.put("update_time",scheduleTag.update_time)
        }

//        scheduleTag.name ?:contentValues.put("name",scheduleTag.name)
//        scheduleTag.title?:contentValues.put("title",scheduleTag.title)
//        scheduleTag.owner?:contentValues.put("owner",scheduleTag.owner)
//        scheduleTag.permission?:contentValues.put("permission",scheduleTag.permission)

        if (contentValues.size() > 0){
            if (scheduleTag.id == -1L ){
                val id = db.insert(NAME_SCHEDULE_TAG,null,contentValues)
                scheduleTag.id = id
            }
            else{
                db.update(NAME_SCHEDULE_TAG,contentValues,"id = ?", arrayOf(scheduleTag.id.toString()))

            }
        }

        try {
            db.close()
        }catch (e:SQLiteException){
            e.printStackTrace()
        }

    }

    fun getScheduleTagValues():ArrayList<ScheduleTag>{
        val db:SQLiteDatabase = this.readableDatabase
        val list:ArrayList<ScheduleTag> = ArrayList()
        val cursor = db.rawQuery("select * from $NAME_SCHEDULE_TAG", arrayOf<String>())

        if (cursor.moveToFirst() && cursor.count > 0){
            do{
                list.add(ScheduleTag(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("owner")),
                    cursor.getInt(cursor.getColumnIndex("icon")),
                    0,
                    cursor.getString(cursor.getColumnIndex("users")),
                    cursor.getString(cursor.getColumnIndex("create_time")),
                    cursor.getString(cursor.getColumnIndex("update_time"))
                ))
            }while (cursor.moveToNext())
        }

        try {
            cursor.close()
            db.close()
        }catch (e:SQLiteException){
            e.printStackTrace()
        }

        return list
    }

    fun fillScheduleTag(tags:ArrayList<ScheduleTag>){
        val db:SQLiteDatabase = this.readableDatabase
        //val list:ArrayList<ScheduleTag> = ArrayList()
        tags.clear()
        val cursor = db.rawQuery("select * from $NAME_SCHEDULE_TAG", arrayOf<String>())

        if (cursor.moveToFirst() && cursor.count > 0){
            do{
                tags.add(ScheduleTag(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("owner")),
                    cursor.getInt(cursor.getColumnIndex("icon")),
                    0,
                    cursor.getString(cursor.getColumnIndex("users")),
                    cursor.getString(cursor.getColumnIndex("create_time")),
                    cursor.getString(cursor.getColumnIndex("update_time"))
                ))
            }while (cursor.moveToNext())
        }

        try {
            cursor.close()
            db.close()
        }catch (e:SQLiteException){
            e.printStackTrace()
        }
    }

    companion object {
        private const val DB_NAME = "myday.db"
        private const val DB_VERSION=1

        private const val NAME_NAME_VALUE = "name_value"
        private const val NAME_SCHEDULE ="schedule"
        private const val NAME_SCHEDULE_TAG = "schedule_tag"

        // 键值对数据
        private const val STRUCT_NAME_VALUE = "create table $NAME_NAME_VALUE(id integer primary key autoincrement,name text,value text)";
        // 日程数据
        //private const val STRUCT_SCHEDULE:String = "create table $NAME_SCHEDULE(id integer primary key autoincrement,username text,start_time text,end_time text,alarm text,repeat text,title text,location text,remark text,tag text,steps text)"

        // 日程数据
        /**
         * #tag:标签 enum_of(::tag_id),count = 0~3 exp:1|4|7
         * #title:标题
         * #start_date:开始日期 default:null exp:2019-03-22
         * #end_date: 结束日期 default:null exp:2019-03-24
         * #start_time: 开始时间 default:null exp:08:44
         * #during: 持续时间 default:null exp:4:57
         * (start_time,during) 若为 (null,null)，表示为全天事件，若仅start_time为null，则表示仅时刻事件
         * #repeat: 重复规则
         * #alarm: 闹钟,日期时间型 default:null exp:2019-03-22 14:55:00
         * #location: 地点
         * #star: 是否为标星日程 (true/false)
         * #remark: 备注
         * #completed: 是否完成 (true/false/undefined)
         * #steps: 步骤，符合json格式 jarrayof(start,during,text)
         */
        private const val STRUCT_SCHEDULE = "create table $NAME_SCHEDULE(id integer primary key autoincrement,tag varchar(30),title varchar(20),start_date varchar(20),end_date varchar(20),start_time varchar(20),during varchar(10),repeat varchar(30),alarm varchar(20),location varchar(20),star varchar(10),completed varchar(10),remark text,steps text)"

        // 日程标签数据
        /**
         * id: 唯一标识符
         * name: 标签名 #TEAM.1172
         * title: 显示名称
         * owner: 拥有者名称
         * icon：图标
         * users: 使用用户列表(包括权限和用户id)
         * create_time: 创建时间
         * update_time: 最近更新时间
         */
        private const val STRUCT_SCHEDULE_TAG = "create table $NAME_SCHEDULE_TAG(id integer primary key autoincrement,name varchar(30),title varchar(20),owner varchar(20),icon integer,users text,create_time varchar(20),update_time varchar(20))"

        private var _instance: DBHelper? = null
        fun getInstance(context:Context): DBHelper {
            if (_instance == null)
                _instance = DBHelper(context);

            return _instance ?:throw AssertionError("An error occured!!!")
        }

        const val SCHEDULE_TAG_INDEX = "schedule_tag_index"
    }
}



                          