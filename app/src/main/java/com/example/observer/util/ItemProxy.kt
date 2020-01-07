package com.example.observer.util

import io.reactivex.Observable
import org.jsoup.nodes.Document

interface ItemProxy {
    fun getJsoupProx(url: String): Observable<Document>
}