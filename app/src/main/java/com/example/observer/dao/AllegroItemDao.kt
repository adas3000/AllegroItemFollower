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

    @Query("update allegroitem set price=:price where uid=:uid")
    fun updatePrice(price:Float,uid:Int)

    @Query("update allegroitem set lastupdate=:lastupdate where uid=:uid")
    fun updateLastUpdate(lastupdate:String,uid:Int)

    @Insert
    fun insert(item: AllegroItem)

    @Delete
    fun delete(item: AllegroItem)

    @Query("delete from allegroitem where uid = :uid ")
    fun deleteById(uid: Int)

    @Update
    fun update(item: AllegroItem)
}