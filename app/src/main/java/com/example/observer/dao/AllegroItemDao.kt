package com.example.observer.dao

import androidx.room.*
import com.example.observer.model.AllegroItem

@Dao
interface AllegroItemDao {

    @Query("SELECT * FROM allegroitem")
    fun getAll():List<AllegroItem>

    @Insert
    fun insert(item:AllegroItem)

    @Delete
    fun delete(item:AllegroItem)

    @Update
    fun update(item:AllegroItem)
}