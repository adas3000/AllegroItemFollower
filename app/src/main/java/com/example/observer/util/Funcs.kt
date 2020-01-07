package com.example.observer.util

import java.util.regex.Matcher
import java.util.regex.Pattern

fun isAllegroPage(url:String):Boolean{


    val begin:String= "https://allegro.pl"

    val pattern :Pattern= Pattern.compile(begin)
    val matcher:Matcher = pattern.matcher(url)





    return matcher.find()
}