package com.muhammedkursatgokgun.artbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedkursatgokgun.artbook.model.Art

@Database(entities = [Art::class], version = 1)
abstract class database : RoomDatabase() {
    abstract fun dao() : Dao
}