package com.example.observer.presenter

import org.jsoup.nodes.Document

interface IAddItemPresenter {
    fun scanURL(url:String)
    fun checkPriceAndFinish(document: Document)
}