package com.example.observer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.observer.R
import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem
import com.example.observer.presenter.JsoupUrlPresenter
import com.example.observer.util.hideKeyboardInFragment
import com.example.observer.util.isAllegroPage
import com.example.observer.view.IJsoupUrlView
import es.dmoral.toasty.Toasty
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_item_fragment_layout.*

class AddItemFragment : Fragment() , IJsoupUrlView {

    companion object{
        private var TAG="AddItemFragment"
    }

    private fun onURLEditingFinished(){

        hideKeyboardInFragment(activity!!.applicationContext,view)


        val url = editText_item_url.text.toString()

        Log.d(TAG,"onURLEditingFinished")
        Log.d(TAG,"Content: "+url)

        if(!isAllegroPage(url)){
            Toasty.info(activity!!.applicationContext,"Incorrect url",Toasty.LENGTH_LONG).show()
            return
        }

        JsoupUrlPresenter(this).scanURL(url)
    }

    override fun onStart() {
        super.onStart()

        editText_item_url.setOnEditorActionListener { v, actionId, event ->

            when(actionId){
                EditorInfo.IME_ACTION_SEARCH,EditorInfo.IME_ACTION_DONE,EditorInfo.IME_ACTION_PREVIOUS-> {
                    this.onURLEditingFinished()
                    true
                }
                else -> false
            }

        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_item_fragment_layout, container, false)
    }

    override fun onError(msg: String,disposable: Disposable) {
        Toasty.error(activity!!.applicationContext,msg,Toasty.LENGTH_SHORT).show()
        disposable.dispose()
    }

    override fun onScanFinishedSuccess(title: String,price:Float,disposable:Disposable) {
        disposable.dispose()

        val db = Room.databaseBuilder(activity!!.applicationContext,AppDatabase::class.java,
            "allegroitemdb1").build()


        Thread(Runnable {
            db.allegroItemDao().insert(AllegroItem(0,title,price,editText_item_url.text.toString()))
        }).start()


        val xd = db.allegroItemDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object:Observer<List<AllegroItem>>{
                override fun onComplete() {
                    Log.d(TAG,"on complete invoked")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,"on error invoked")
                    Log.d(TAG,e.message)
                }

                override fun onNext(t: List<AllegroItem>) {
                    Log.d(TAG,"on next invoked")
                    Log.d(TAG,"allegroitems size:"+t.size)

                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG,"on subscribe invoked")
                }
            })


    }

}