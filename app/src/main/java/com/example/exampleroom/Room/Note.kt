package com.example.exampleroom.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var title : String,
    var desc : String

        )
