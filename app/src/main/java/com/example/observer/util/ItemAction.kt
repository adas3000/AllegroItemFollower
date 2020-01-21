package com.example.observer.util

import android.widget.ImageView
import com.example.observer.model.AllegroItem

interface ItemAction {
    fun onUrlClick(url:String)
    fun onRemoveClick(id:Int)
    fun setItemImage(url:String,imageView: ImageView)
    fun getString(item:AllegroItem):String
}