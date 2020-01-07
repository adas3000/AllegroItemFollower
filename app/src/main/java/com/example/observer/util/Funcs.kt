package com.example.observer.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.regex.Matcher
import java.util.regex.Pattern

fun isAllegroPage(url:String):Boolean{


    val begin:String= "^https://allegro.pl/oferta/"

    val pattern :Pattern= Pattern.compile(begin)
    val matcher:Matcher = pattern.matcher(url)


    return matcher.find()
}

fun hideKeyboardInFragment(context: Context,view:View?){
    val imm :InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view!!.windowToken,0)
}

fun textToFloat(str:String):Float{

    var new_str :String= str.replace(" ","")
    new_str = new_str.replace(",",".")


    return new_str.toFloat()
}