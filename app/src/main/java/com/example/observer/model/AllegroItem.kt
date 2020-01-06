package com.example.observer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AllegroItem {

    @PrimaryKey
    val uid:Int

    @ColumnInfo(name = "item_name")
    var itemName:String?

    @ColumnInfo(name="price")
    var itemPrice:Int

    @ColumnInfo(name="url")
    var itemURL:String?

    constructor(uid: Int, itemName: String, itemPrice: Int, itemURL: String) {
        this.uid = uid
        this.itemName = itemName
        this.itemPrice = itemPrice
        this.itemURL = itemURL
    }
}