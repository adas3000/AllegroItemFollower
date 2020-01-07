package com.example.observer.dao

import androidx.room.*
import com.example.observer.model.AllegroItem
import io.reactivex.Observable

@Dao
interface AllegroItemDao {

    @Query("SELECT * FROM allegroitem")
    fun getAll():Observable<List<AllegroItem>>

    @Query("select * from allegroitem where url like :url ")
    fun itemExist(url:String):List<AllegroItem>
    
    @Insert
    fun insert(item:AllegroItem)

    @Delete
    fun delete(item:AllegroItem)

    @Update
    fun update(item:AllegroItem)
}