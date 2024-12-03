package com.example.pr21_dorofeev

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, SCHEMA) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE users (" + COLUMN_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                    + " TEXT, " + COLUMN_YEAR + " INTEGER);"
        )
        // добавление начальных данных
        db.execSQL(
            "INSERT INTO " + TABLE + " (" + COLUMN_NAME
                    + ", " + COLUMN_YEAR + ") VALUES ('Том Смит', 1981);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "userstore.db" // название бд
        private const val SCHEMA = 1 // версия базы данных
        const val TABLE: String = "users" // название таблицы в бд

        // названия столбцов
        const val COLUMN_ID: String = "_id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_YEAR: String = "year"
    }
}
