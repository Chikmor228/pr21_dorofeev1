package com.example.pr21_dorofeev

import android.R
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.getIntent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity


class UserActivity() : AppCompatActivity() {
    var nameBox: EditText? = null
    var yearBox: EditText? = null
    var delButton: Button? = null
    var saveButton: Button? = null

    var sqlHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var userCursor: Cursor? = null
    var userId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        nameBox = findViewById(R.id.name)
        yearBox = findViewById(R.id.year)
        delButton = findViewById(R.id.deleteButton)
        saveButton = findViewById(R.id.saveButton)

        sqlHelper = DatabaseHelper(this)
        db = sqlHelper!!.writableDatabase

        val extras = getIntent().extras
        if (extras != null) {
            userId = extras.getLong("id")
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery(
                "select * from " + DatabaseHelper.TABLE + " where " +
                        DatabaseHelper.COLUMN_ID + "=?", arrayOf(userId.toString())
            )
            userCursor.moveToFirst()
            nameBox!!.setText(userCursor.getString(1))
            yearBox!!.setText(userCursor.getInt(2).toString())
            userCursor.close()
        } else {
            // скрываем кнопку удаления
            delButton!!.visibility = View.GONE
        }
    }

    private fun setContentView(activityUser: Any) {
        try {
            TODO("Not yet implemented")
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
    }

    fun save(view: View?) {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_NAME, nameBox!!.text.toString())
        cv.put(DatabaseHelper.COLUMN_YEAR, yearBox!!.text.toString().toInt())

        if (userId > 0) {
            db!!.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null)
        } else {
            db!!.insert(DatabaseHelper.TABLE, null, cv)
        }
        goHome()
    }

    fun delete(view: View?) {
        db!!.delete(DatabaseHelper.TABLE, "_id = ?", arrayOf(userId.toString()))
        goHome()
    }

    private fun goHome() {
        // закрываем подключение
        db!!.close()
        // переход к главной activity
        val intent: Intent = Intent(
            this,
            MainActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}
