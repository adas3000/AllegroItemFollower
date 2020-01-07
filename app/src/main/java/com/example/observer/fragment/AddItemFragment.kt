package com.example.observer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.example.observer.R
import kotlinx.android.synthetic.main.add_item_fragment_layout.*

class AddItemFragment : Fragment() {

    companion object{
        private var TAG="AddItemFragment"
    }

    private fun onURLEditingFinished(){
        val url = editText_item_url.text.toString()

        Log.d(TAG,"onURLEditingFinished")
        Log.d(TAG,"Content: "+url)





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




}