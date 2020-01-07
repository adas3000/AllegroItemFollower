package com.example.observer.presenter

import com.example.observer.view.IJsoupUrlView
import io.reactivex.Observable
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.RuntimeException

class JsoupUrlPresenter(jsoupurlView: IJsoupUrlView) : IJsoupUrlPresenter {


    private fun getJsoupProx(url:String):Observable<String>?{

      return null
    }


    override fun scanURL(url: String) {

    }


}