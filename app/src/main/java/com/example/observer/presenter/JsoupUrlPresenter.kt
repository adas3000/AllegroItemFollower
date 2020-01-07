package com.example.observer.presenter

import com.example.observer.view.IJsoupUrlView
import io.reactivex.Observable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.RuntimeException

class JsoupUrlPresenter(jsoupurlView: IJsoupUrlView) : IJsoupUrlPresenter {


    private fun getJsoupProx(url:String):Observable<Document>{

        return Observable.fromCallable<Document>{
            try{
                val doc = Jsoup.connect(url).get()
                return@fromCallable doc
            }
            catch(e:IOException){
                throw RuntimeException(e)
            }
        }
    }


    override fun scanURL(url: String) {

        


    }


}