package com.example.observer.presenter

import android.util.Log
import com.example.observer.enums.AllegroDivInstance
import com.example.observer.util.textToFloat
import com.example.observer.view.IJsoupUrlView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.NumberFormatException
import java.lang.RuntimeException

class JsoupUrlPresenter : IJsoupUrlPresenter {

    private val TAG = "JsoupUrlPresenter"

    val jsoupurlView:IJsoupUrlView
    lateinit var disposable: Disposable

    constructor(jsoupurlView:IJsoupUrlView) {
        this.jsoupurlView = jsoupurlView
    }

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

     override fun checkPriceAndFinish(document: Document) {

         val title:String = document.title()
         val str_price:String = document.selectFirst(AllegroDivInstance.Instance.div).text()

         try{
             val float_price:Float = textToFloat(str_price)
             Log.d(TAG,"price:"+float_price.toString())
             jsoupurlView.onScanFinishedSuccess(title,float_price,disposable)
         }
         catch(e:NumberFormatException){
             e.fillInStackTrace()
             jsoupurlView.onError(e.message.toString(),disposable)
         }

    }


    private fun getObserver(): Observer<Document> {

        return object : Observer<Document> {
            override fun onComplete() {
                Log.d(TAG, "onComplete invoked")
            }

            override fun onNext(t: Document) {
                Log.d(TAG, "onNext invoked")
                Log.d(TAG,"Title:"+t.title())
                checkPriceAndFinish(t)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribed invoked")
                disposable = d
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError invoked")
                Log.d(TAG,e.message)
                jsoupurlView.onError(e.message.toString(),disposable)
            }
        }
    }
}