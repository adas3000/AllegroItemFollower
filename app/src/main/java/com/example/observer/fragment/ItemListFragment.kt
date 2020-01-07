package com.example.observer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.observer.R
import com.example.observer.adapter.ItemListAdapter
import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem
import com.example.observer.presenter.IItemListPresenter
import com.example.observer.presenter.ItemListPresenter
import com.example.observer.view.ItemListView
import es.dmoral.toasty.Toasty
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.itemlist_fragment_layout.*

class ItemListFragment : Fragment(),ItemListView {


    override fun onStart() {
        super.onStart()
        val itemListPresenter:IItemListPresenter = ItemListPresenter(this)
        itemListPresenter.onGetAllItems(
            Room.databaseBuilder(activity!!.applicationContext, AppDatabase::class.java,
                "allegroitemdb1").build())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.itemlist_fragment_layout,container,false)
    }


    override fun onError(msg: String,disposable: Disposable) {
        disposable.dispose()

        Toasty.error(activity!!.applicationContext,msg,Toasty.LENGTH_SHORT).show()
    }

    override fun onFinish(list: List<AllegroItem>,disposable: Disposable) {
        disposable.dispose()

        rv_items.layoutManager = LinearLayoutManager(activity!!.applicationContext,RecyclerView.VERTICAL,false)
        rv_items.setHasFixedSize(true)
        rv_items.adapter = ItemListAdapter(list)

    }


}