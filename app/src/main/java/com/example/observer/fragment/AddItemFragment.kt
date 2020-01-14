package com.example.observer.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.observer.R
import com.example.observer.db.AppDatabase
import com.example.observer.db.GetDbInstance
import com.example.observer.model.AllegroItem
import com.example.observer.presenter.AddItemPresenter
import com.example.observer.util.ItemAdded
import com.example.observer.util.hideKeyboardInFragment
import com.example.observer.util.isAllegroPage
import com.example.observer.view.IAddItemView
import es.dmoral.toasty.Toasty
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_item_fragment_layout.*

class AddItemFragment : Fragment(), IAddItemView {

    private val ITEM_ADDED_KEY = "ITEM_ADD_KEY"

    companion object {
        private var TAG = "AddItemFragment"

        @JvmStatic
        fun newInstance(itemAdded: ItemAdded) = AddItemFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ITEM_ADDED_KEY,itemAdded)
            }
        }.apply {
            this.itemAdded = itemAdded
        }

    }

    lateinit var db:AppDatabase
    lateinit var itemAdded: ItemAdded

    private fun onURLEditingFinished() {

        hideKeyboardInFragment(activity!!.applicationContext, view)


        val url = editText_item_url.text.toString()

        Log.d(TAG, "onURLEditingFinished")
        Log.d(TAG, "Content: " + url)

        if (!isAllegroPage(url)) {
            Toasty.info(activity!!.applicationContext, "Incorrect url", Toasty.LENGTH_LONG).show()
            return
        }

        AddItemPresenter(this).scanURL(url)
    }

    override fun onStart() {
        super.onStart()

        add_item_Button.setOnClickListener {
            itemAdded.setAdded(false)
            if (!progressBar_addItem.isVisible) {
                progressBar_addItem.visibility = View.VISIBLE
                onURLEditingFinished()
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_fragment_layout, container, false)
    }

    override fun onError(msg: String, disposable: Disposable) {
        Toasty.error(activity!!.applicationContext, msg, Toasty.LENGTH_SHORT).show()
        disposable.dispose()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = GetDbInstance.getDb(context)
    }

    override fun onScanFinishedSuccess(title: String, price: Float, img_url: String, disposable: Disposable) {
        disposable.dispose()

        Thread(Runnable {
            val allegroItem = AllegroItem(0, title, price, editText_item_url.text.toString())
            allegroItem.itemImgUrl = img_url
            db.allegroItemDao().insert(allegroItem)
        }).start()

        progressBar_addItem.visibility = View.INVISIBLE

        Toasty.success(activity!!.applicationContext, "Added!", Toasty.LENGTH_SHORT).show()

        itemAdded.setAdded(true)
    }

}