package com.example.task.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "record")
data class Entiti(@PrimaryKey(autoGenerate = true)
                  var id:Int,
                  var title:String?=null, var date:String?=null,var hour:String?=null,var mint:String?=null) : Parcelable