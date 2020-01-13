package com.example.observer.util

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
            internetPresenter.onAvailable(db)
            Thread.sleep(10000)
        }

    }

}