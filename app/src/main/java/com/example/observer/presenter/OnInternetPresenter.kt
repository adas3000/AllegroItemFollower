package com.example.observer.presenter

import android.util.Log
import com.example.observer.db.AppDatabase
import com.example.observer.util.ItemProxy
import com.example.observer.view.IOnInternetView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.RuntimeException

class OnInternetPresenter : IOnInternetPresenter,IItemListPresenter,ItemProxy {

    val onInternetView:IOnInternetView
    private val TAG="OnInternetPresenter"

    constructor(onInternetView: IOnInternetView) {
        this.onInternetView = onInternetView
    }

    override fun onGetAllItems(db: AppDatabase) {
        db.allegroItemDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
           
    }

    override fun onAvailable(db: AppDatabase) {

        //1.findAll
        //2.checkAll using Jsoup if price is different from current notify




    }

    override fun getJsoupProx(url: String): Observable<Document> {
        return Observable.fromCallable<Document> {
            try {
                val doc = Jsoup.connect(url).get()
                return@fromCallable doc
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }


}
