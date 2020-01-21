package com.example.observer.enums

enum class AllegroDivInstance {

    Instance("h1._1sjrk","div._9a071_1Q_68","img._9a071_2_eNL","div._9a071_Phfa8");

    val div:String
    val img:String
    val title:String
    val expiredIn:String

    constructor(title:String,div:String,img:String,expiredIn:String){
        this.title = title
        this.div = div
        this.img = img
        this.expiredIn = expiredIn
    }

}