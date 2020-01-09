package com.example.observer.presenter

import android.util.Log
import com.example.observer.db.AppDatabase
import com.example.observer.enums.AllegroDivInstance
import com.example.observer.model.AllegroItem
import com.example.observer.util.IItemListObserver
import com.example.observer.util.ItemProxy
import com.example.observer.util.textToFloat
import com.example.observer.view.IOnInternetView
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

class OnInternetPresenter : IOnInternetPresenter,IItemListPresenter,ItemProxy,IItemListObserver {

    val onInternetView:IOnInternetView
    private val TAG="OnInternetPresenter"
    lateinit var dispose:Disposable

    //todo make all disposables dispose

    constructor(onInternetView: IOnInternetView) {
        this.onInternetView = onInternetView
    }

    override fun onGetAllItems(db: AppDatabase) {
        db.allegroItemDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getItemListObserver())
    }

    override fun onAvailable(db: AppDatabase) {
        onGetAllItems(db)
        //1.findAll
        //2.checkAll using Jsoup if price is different from current notify
    }

    override fun doCheck(itemList: List<AllegroItem>) {
        doDispose(dispose)
        for(item:AllegroItem in itemList){
            getJsoupProxy(item.itemURL.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getDocumentObserver(item))
        }

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

    override fun compareItems(allegroItem: AllegroItem, document: Document) {

        val title:String = document.title()
        val str_price:String = document.selectFirst(AllegroDivInstance.Instance.div).text()

        try{
            val float_price:Float = textToFloat(str_price)

            if(float_price!=allegroItem.itemPrice){
                onInternetView.onPriceChanged(title,float_price,allegroItem.itemURL.toString(),allegroItem.uid)
            }
            //onInternetView.onPriceChanged(title,float_price,allegroItem.uid) -->> for tests
            Log.d(TAG,"price:"+float_price.toString())
            doDispose(dispose)
        }
        catch(e: NumberFormatException){
            e.fillInStackTrace()
            onInternetView.onError(e.message.toString())
        }


    }


    fun getDocumentObserver(allegroItem: AllegroItem): Observer<Document> {
        return object : Observer<Document> {
            override fun onComplete() {
                Log.d(TAG, "onComplete invoked")
            }

            override fun onNext(t: Document) {
                Log.d(TAG, "onNext invoked")
                compareItems(allegroItem,t)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribed invoked")
                dispose = d
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError invoked")
                Log.d(TAG,e.message)
            }
        }
    }

    override fun getItemListObserver(): Observer<List<AllegroItem>> {
        return object:Observer<List<AllegroItem>>{

            override fun onComplete() {
                Log.d(TAG, "onComplete invoked")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG,"on error invoked")
                Log.d(TAG,e.message)
            }

            override fun onNext(t: List<AllegroItem>) {
                Log.d(TAG,"on next invoked")
                doCheck(t)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG,"on subscribe invoked")
                dispose = d
            }

        }
    }

    fun doDispose(d:Disposable){
        d.dispose()
    }

}
