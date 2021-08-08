package com.example.exampleroom.Room

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addData(note : Note)

    @Update
    suspend fun UpdateData(note : Note)

    @Delete
    suspend fun DeleteData(note : Note)

    @Query("SELECT * FROM note")
    suspend fun getData() : List<Note>

    @Query("SELECT * FROM note WHERE id=:note_id")
    suspend fun getDatabyId(note_id : Int) : List<Note>
}