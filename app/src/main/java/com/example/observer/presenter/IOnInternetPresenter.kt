package com.example.observer.presenter

import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem
import org.jsoup.nodes.Document

interface IOnInternetPresenter {
    fun doCheck(itemList:List<AllegroItem>)
    fun compareItems(allegroItem: AllegroItem,document: Document)
}