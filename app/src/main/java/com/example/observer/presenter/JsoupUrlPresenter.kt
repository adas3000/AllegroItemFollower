package com.example.observer.presenter

import android.util.Log
import com.example.observer.view.IJsoupUrlView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.RuntimeException

class JsoupUrlPresenter(jsoupurlView: IJsoupUrlView) : IJsoupUrlPresenter {

    private val TAG = "JsoupUrlPresenter"

    private fun getJsoupProx(url: String): Observable<Document> {

        return Observable.fromCallable<Document> {
            try {
                val doc = Jsoup.connect(url).get()
                return@fromCallable doc
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }


    override fun scanURL(url: String) {

        val observer: Observer<Document> = getObserver()

        getJsoupProx(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)


    }

    private fun getObserver(): Observer<Document> {
        return object : Observer<Document> {
            override fun onComplete() {
                Log.d(TAG, "onComplete invoked")
            }

            override fun onNext(t: Document) {
                Log.d(TAG, "onNext invoked")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribed invoked")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError invoked")
            }
        }
    }

}