package com.example.observer.util

import io.reactivex.Observer
import org.jsoup.nodes.Document

interface IDocumentObserver {
    fun getDocumentObserver(): Observer<Document>
}