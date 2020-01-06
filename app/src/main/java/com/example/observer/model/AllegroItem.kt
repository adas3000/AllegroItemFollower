package com.example.observer.model

class AllegroItem {

    var itemName:String
    var itemPrice:Int
    var itemURL:String

    constructor(itemName: String, itemPrice: Int, itemURL: String) {
        this.itemName = itemName
        this.itemPrice = itemPrice
        this.itemURL = itemURL
    }
}