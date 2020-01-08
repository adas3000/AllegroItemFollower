package com.example.observer.view

import io.reactivex.disposables.Disposable

interface IAddItemView {
    fun onError(msg:String,disposable:Disposable)
    fun onScanFinishedSuccess(title:String,price:Float,img_url:String,disposable:Disposable)
}