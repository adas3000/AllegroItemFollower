package com.example.observer.util

import android.widget.ImageView

interface ItemAction {
    fun onUrlClick(url:String)
    fun onRemoveClick(id:Int)
    fun setItemImage(url:String,imageView: ImageView)
}