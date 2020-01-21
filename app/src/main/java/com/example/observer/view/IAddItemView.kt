package com.example.observer.view

import com.example.observer.model.AllegroItem
import io.reactivex.disposables.Disposable

interface IAddItemView {
    fun onError(msg:String)
    fun onScanFinishedSuccess(allegroItem: AllegroItem)
}