package com.example.observer.util

import com.example.observer.model.AllegroItem
import io.reactivex.Observer

interface IItemListObserver {
    fun getItemListObserver(): Observer<List<AllegroItem>>
}