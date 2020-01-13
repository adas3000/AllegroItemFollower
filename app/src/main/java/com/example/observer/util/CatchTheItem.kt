package com.example.observer.util

import android.util.Log
import com.example.observer.db.AppDatabase
import com.example.observer.presenter.IOnInternetPresenter

class CatchTheItem : Runnable {

    val internetPresenter:IOnInternetPresenter
    val db:AppDatabase
    var do_run = false

    constructor(internetPresenter: IOnInternetPresenter, db: AppDatabase,do_run:Boolean = false) {
        this.internetPresenter = internetPresenter
        this.db = db
        this.do_run = do_run
    }


    override fun run() {

        while(do_run){
            Thread.sleep(10000)
            internetPresenter.onAvailable(db)
        }

    }

}