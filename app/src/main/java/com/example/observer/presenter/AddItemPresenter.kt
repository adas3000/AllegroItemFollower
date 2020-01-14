package com.example.observer.presenter

import android.util.Log
import com.example.observer.enums.AllegroDivInstance
import com.example.observer.util.IDocumentObserver
import com.example.observer.util.ItemProxy
import com.example.observer.util.textToFloat
import com.example.observer.view.IAddItemView
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

class AddItemPresenter : IAddItemPresenter, ItemProxy, IDocumentObserver {

    private val TAG = "AddItemPresenter"

    val jsoupurlView: IAddItemView
    val optionalItemName: String
    lateinit var disposable: Disposable

    constructor(jsoupurlView: IAddItemView, optionalItemName: String = "") {
        this.jsoupurlView = jsoupurlView
        this.optionalItemName = optionalItemName
    }

    override fun getJsoupProxy(url: String): Observable<Document> {

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

        val observer: Observer<Document> = getDocumentObserver()

        getJsoupProxy(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)


    }

    override fun checkPriceAndFinish(document: Document) {
        val title: String

        if (optionalItemName.length > 0) title = optionalItemName
        else title = document.selectFirst(AllegroDivInstance.Instance.title).text()

        val str_price: String = document.selectFirst(AllegroDivInstance.Instance.div).text()
        val img_url: String = document.selectFirst(AllegroDivInstance.Instance.img).absUrl("src")

        try {
            val float_price: Float = textToFloat(str_price)
            Log.d(TAG, "price:" + float_price.toString())
            jsoupurlView.onScanFinishedSuccess(title, float_price, img_url, disposable)
        } catch (e: NumberFormatException) {
            e.fillInStackTrace()
            jsoupurlView.onError(e.message.toString(), disposable)
        }

    }

    override fun getDocumentObserver(): Observer<Document> {
        return object : Observer<Document> {
            override fun onComplete() {
                Log.d(TAG, "onComplete invoked")
            }

            override fun onNext(t: Document) {
                Log.d(TAG, "onNext invoked")
                checkPriceAndFinish(t)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribed invoked")
                disposable = d
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError invoked")
                Log.d(TAG, e.message)
                jsoupurlView.onError(e.message.toString(), disposable)
            }
        }
    }

}