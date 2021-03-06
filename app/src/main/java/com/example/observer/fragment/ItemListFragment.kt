package com.example.observer.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.observer.R
import com.example.observer.adapter.ItemListAdapter
import com.example.observer.component.DaggerMainActivityComponent
import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem
import com.example.observer.module.MainActivityModule
import com.example.observer.presenter.IItemListPresenter
import com.example.observer.presenter.ItemListPresenter
import com.example.observer.util.ItemAction
import com.example.observer.view.ItemListView
import es.dmoral.toasty.Toasty
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.itemlist_fragment_layout.*

class ItemListFragment : Fragment(),ItemListView,ItemAction {

    lateinit var db:AppDatabase

    override fun onStart() {
        super.onStart()

        db = DaggerMainActivityComponent
                .builder()
                .mainActivityModule(MainActivityModule(context!!.applicationContext))
                .build()
                .getDatabaseInstance()

        val itemListPresenter:IItemListPresenter = ItemListPresenter(this)
        itemListPresenter.onGetAllItems(db)

        pullToRefresh.setOnRefreshListener {
            reloadFragment()
        }
    }

    override fun getString(item: AllegroItem):String {
        return getString(R.string.item_content_text,item.itemName,item.itemPrice.toString(),item.expiredIn,item.lastUpdate)
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
        rv_items.adapter = ItemListAdapter(list,this)

    }

    override fun onUrlClick(url:String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onRemoveClick(id: Int) {

        val alertDialog = AlertDialog.Builder(activity)
            .setCancelable(false)
            .setTitle("Remove")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes",{dialog, which ->

                Thread(Runnable {
                    db.allegroItemDao().deleteById(id)
                }).start()
                reloadFragment()
            })
            .setNegativeButton("No",{dialog, which ->
                dialog.cancel()
            })
            .create()
        alertDialog.show()
    }

    override fun setItemImage(url: String, imageView: ImageView) {
            Glide.with(activity!!.applicationContext)
                .load(url)
                //.apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    private fun reloadFragment(){
        val currentFragment:Fragment = activity!!.supportFragmentManager.findFragmentById(R.id.frameLayout_fragmentKeeper) as Fragment
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.detach(currentFragment)
        fragmentTransaction.attach(currentFragment)
        fragmentTransaction.commit()
    }

}