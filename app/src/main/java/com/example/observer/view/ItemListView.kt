package com.example.observer.view

import com.example.observer.model.AllegroItem
import io.reactivex.disposables.Disposable

interface ItemListView {
    fun onError(msg:String,disposable: Disposable)
    fun onFinish(list:List<AllegroItem>,disposable: Disposable)
}