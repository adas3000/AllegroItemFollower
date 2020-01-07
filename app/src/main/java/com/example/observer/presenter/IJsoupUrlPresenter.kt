package com.example.observer.presenter

import org.jsoup.nodes.Document

interface IJsoupUrlPresenter {
    fun scanURL(url:String)
    fun checkPriceAndAddToUserList(document: Document)
}