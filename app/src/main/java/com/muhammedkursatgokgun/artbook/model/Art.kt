package com.muhammedkursatgokgun.artbook.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Art(
    @ColumnInfo var name: String,
    @ColumnInfo var comment: String,
    @ColumnInfo var image: ByteArray
    ){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
