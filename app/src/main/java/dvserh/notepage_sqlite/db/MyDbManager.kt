package com.example.lessonsqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import dvserh.notepage_sqlite.db.ListItem

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB(){
        db = myDbHelper.writableDatabase
    }
    fun insertToDb (title: String, content: String, uri: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_URI, uri)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }
    fun readDbData(): ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null, null, null, null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dateTitle = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataContent = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataUri = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_URI))
                val item = ListItem()
                    item.title = dateTitle!!
                    item.descr = dataContent!!
                    item.uri = dataUri!!
                    dataList.add(item)
                }
        }
        cursor?.close()
        return dataList
    }
    fun closeDb (){
        myDbHelper.close()
    }

}