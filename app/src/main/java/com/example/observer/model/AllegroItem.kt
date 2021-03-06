package com.example.observer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AllegroItem {

    @PrimaryKey(autoGenerate = true)
    val uid:Int

    @ColumnInfo(name = "item_name")
    var itemName:String?

    @ColumnInfo(name="price")
    var itemPrice:Float

    @ColumnInfo(name="url")
    var itemURL:String?

    @ColumnInfo(name="img")
    var itemImgUrl:String?=""

    @ColumnInfo(name="expiredin")
    var expiredIn:String?=""

    @ColumnInfo(name="lastupdate")
    var lastUpdate:String?=""

    constructor(uid: Int, itemName: String, itemPrice: Float, itemURL: String) {
        this.uid = uid
        this.itemName = itemName
        this.itemPrice = itemPrice
        this.itemURL = itemURL
    }
}