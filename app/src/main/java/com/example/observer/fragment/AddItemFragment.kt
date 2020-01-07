package com.example.observer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.observer.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.add_item_fragment_layout.*

class AddItemFragment : Fragment() {

    companion object{
        private var TAG="AddItemFragment"
    }


    override fun onStart() {
        super.onStart()

        val observable=Observable.just(editText_item_url)

        val observer = object:Observer<EditText>{
            override fun onComplete() {
                Log.d(TAG,"onComplete invoked")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG,"onError invoked")
            }

            override fun onNext(t: EditText) {
                Log.d(TAG,"onNext invoked")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG,"onSubscribe invoked")
            }
        }

        observable.subscribe(observer)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_item_fragment_layout, container, false)
    }




}