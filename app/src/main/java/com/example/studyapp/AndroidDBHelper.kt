package com.example.studyapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AndroidDBHelper(context: Context): SQLiteOpenHelper(context,"Android.db",null,1) {

    private val sqliteDatabase: SQLiteDatabase = writableDatabase
    companion object{
        const val KEY_ID = "id"
        const val TOPIC_TABLE = "Topic"
        const val TITLE_COLUMN = "title"
        const val INFO_COLUMN = "information"
        const val EXPLAIN_COLUMN = "explanation"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val sqlCode = "CREATE TABLE $TOPIC_TABLE ($KEY_ID INTEGER PRIMARY KEY,$TITLE_COLUMN TEXT, $INFO_COLUMN TEXT, $EXPLAIN_COLUMN LONGTEXT)"
        p0!!.execSQL(sqlCode)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addTopic(topic: Topic):Long{
        val cv = ContentValues()
        cv.put(TITLE_COLUMN,topic.title)
        cv.put(INFO_COLUMN,topic.info)
        cv.put(EXPLAIN_COLUMN,topic.explanation)
        return sqliteDatabase.insert(TOPIC_TABLE,null,cv)
    }

    fun getAllTopics():ArrayList<Topic>{
        val topics = ArrayList<Topic>()
        val sqlCode = "SELECT * FROM $TOPIC_TABLE"
        val c: Cursor = sqliteDatabase.rawQuery(sqlCode,null)

        if(c.moveToFirst()){
            do {
                val id = c.getInt(c.getColumnIndex(KEY_ID))
                val title = c.getString(c.getColumnIndex(TITLE_COLUMN))
                val info = c.getString(c.getColumnIndex(INFO_COLUMN))
                val explain = c.getString(c.getColumnIndex(EXPLAIN_COLUMN))
                val topic = Topic(id,title,info,explain)
                topics.add(topic)
            }while (c.moveToNext())
        }
        return topics
    }

    fun updateTopic(topic: Topic):Int{
        val cv = ContentValues()
        cv.put(TITLE_COLUMN,topic.title)
        cv.put(INFO_COLUMN,topic.info)
        cv.put(EXPLAIN_COLUMN,topic.explanation)
        return sqliteDatabase.update(TOPIC_TABLE,cv,"$KEY_ID=?", arrayOf(topic.id.toString()))
    }

    fun deleteTopic(id:Int):Int{
        return sqliteDatabase.delete(TOPIC_TABLE,"$KEY_ID=?", arrayOf(id.toString()))
    }


}