package com.example.observer.presenter

import android.util.Log
import com.example.observer.db.AppDatabase
import com.example.observer.enums.AllegroDivInstance
import com.example.observer.model.AllegroItem
import com.example.observer.util.ItemProxy
import com.example.observer.util.textToFloat
import com.example.observer.view.IOnInternetView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.NumberFormatException
import java.lang.RuntimeException
import java.time.LocalDateTime

class OnInternetPresenterFix(val onInternetView: IOnInternetView) :
        IOnInternetPresenter, IItemListPresenter, ItemProxy {


    private val TAG = "OnInternetPresenterFix"

    private val disposable = CompositeDisposable()


    override fun onGetAllItems(db: AppDatabase) {
        db.allegroItemDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doCheck(it)
                }).addTo(disposable)
    }

    override fun doCheck(itemList: List<AllegroItem>) {

        val itemObservable: Observable<AllegroItem> = Observable
                .fromIterable(itemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


        itemObservable.subscribe(object : Observer<AllegroItem> {
            override fun onComplete() {
                Log.d(TAG, "OnComplete invoked")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "OnError invoked:" + e.message)
            }

            override fun onNext(t: AllegroItem) {
                Log.d(TAG, "onNext invoked:" + t.itemName)

                val docObservable: Observable<Document> = getJsoupProxy(t.itemURL.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                val item = t
                docObservable.subscribe(object : Observer<Document> {
                    override fun onNext(t: Document) {
                        Log.d(TAG, "onNext invoked into docObservable")
                        compareItems(item, t)
                    }

                    override fun onComplete() {
                        Log.d(TAG,"docObserver onComplete invoked:")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG,"docObserver error invoked:"+e.message.toString())
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }
                })

            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }
        })

    }

    override fun getJsoupProxy(url: String): Observable<Document> {
        return Observable.fromCallable<Document> {
            try {
                return@fromCallable Jsoup.connect(url).get()
            } catch (e: IOException) {
                e.fillInStackTrace()
                throw RuntimeException(e)
            }
        }
    }

    override fun compareItems(allegroItem: AllegroItem, document: Document) {

        val str_price: String = document.selectFirst(AllegroDivInstance.Instance.div).text()

        var expiredIn: String? = ""

        if (document.selectFirst(AllegroDivInstance.Instance.expiredIn) != null)
            expiredIn = document.selectFirst(AllegroDivInstance.Instance.expiredIn).text()


        try {
            val floatPrice: Float = textToFloat(str_price)

            allegroItem.expiredIn = expiredIn
            allegroItem.lastUpdate = LocalDateTime.now().toString()

            if (floatPrice != allegroItem.itemPrice) {
                allegroItem.itemPrice = floatPrice
                onInternetView.onPriceChanged(allegroItem)
            } else onInternetView.onPriceDidNotChanged(allegroItem)
            onInternetView.onPriceChanged(allegroItem) //-->> for tests
            disposable.clear()
        } catch (e: NumberFormatException) {
            e.fillInStackTrace()
            onInternetView.onError(e.message.toString())
        }
    }

}