package com.example.observer.dao

import androidx.room.*
import com.example.observer.model.AllegroItem
import io.reactivex.Observable

@Dao
interface AllegroItemDao {

    @Query("SELECT * FROM allegroitem")
    fun getAll(): Observable<List<AllegroItem>>

    @Query("select * from allegroitem where url like :url ")
    fun itemExist(url: String): Observable<List<AllegroItem>>

    @Insert
    fun insert(item: AllegroItem)

    @Delete
    fun delete(item: AllegroItem)

    @Query("delete from allegroitem where uid = :uid ")
    fun deleteById(uid: Int)

    @Update
    fun update(item: AllegroItem)
}