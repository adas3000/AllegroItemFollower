package com.example.observer.presenter

import android.util.Log
import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem
import com.example.observer.view.ItemListView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ItemListPresenter : IItemListPresenter  {

    private val TAG="ItemListPresenter"

    val itemListView:ItemListView
    lateinit var allegroItemList:List<AllegroItem>
    lateinit var disposable: Disposable

    constructor(itemListView:ItemListView){
        this.itemListView = itemListView
    }

    override fun onGetAllItems(db: AppDatabase) {

        db.allegroItemDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Observer<List<AllegroItem>>{
                override fun onComplete() {
                    Log.d(TAG, "onComplete invoked")
                    itemListView.onFinish(allegroItemList,disposable)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,"on error invoked")
                    Log.d(TAG,e.message)
                    itemListView.onError(e.message.toString(),disposable)
                }

                override fun onNext(t: List<AllegroItem>) {
                    Log.d(TAG,"on next invoked")
                    allegroItemList = t
                    itemListView.onFinish(allegroItemList,disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    Log.d(TAG,"on subscribe invoked")
                }
            })
    }


}