package com.muhammedkursatgokgun.artbook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.muhammedkursatgokgun.artbook.model.Art
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface Dao {
    @Query("SELECT * FROM Art")
    fun getAll(): Flowable<List<Art>>

    @Query("SELECT * FROM Art WHERE id = :id")
    fun getById(id: Int): Flowable<Art>

    @Query("SELECT * FROM Art WHERE name = :name")
    fun findByName(name : String): Art

    @Insert
    fun insert(art: Art) :Completable

    @Delete
    fun delete(art: Art)
}