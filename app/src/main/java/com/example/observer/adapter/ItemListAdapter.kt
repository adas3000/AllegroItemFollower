package com.example.observer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.observer.R
import com.example.observer.model.AllegroItem

class ItemListAdapter(val itemList:List<AllegroItem>):RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.item_name.setText(itemList[position].itemName)
        holder.price.setText(itemList[position].itemPrice.toString())
        holder.url.setText(itemList[position].itemURL)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.itemlist_fragment_layout,parent,false)
        return ViewHolder(v)
    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val url = view.findViewById<TextView>(R.id.item_data_layout_textView_url)
        val item_name = view.findViewById<TextView>(R.id.item_data_layout_textView_title)
        val price = view.findViewById<TextView>(R.id.item_data_layout_textView_price)
    }


}