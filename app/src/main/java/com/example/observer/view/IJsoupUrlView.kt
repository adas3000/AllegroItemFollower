package com.example.observer.view

import io.reactivex.disposables.Disposable

interface IJsoupUrlView {
    fun onError(msg:String,disposable:Disposable)
    fun onScanFinishedSuccess(title:String,price:Float,disposable:Disposable)
}