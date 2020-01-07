package com.example.observer.util

import io.reactivex.Observable
import org.jsoup.nodes.Document

interface ItemProxy {
    fun getJsoupProxy(url: String): Observable<Document>
}