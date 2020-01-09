package com.example.observer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.observer.R
import com.example.observer.model.AllegroItem
import com.example.observer.util.ItemAction

class ItemListAdapter(val itemList:List<AllegroItem>,val itemAction: ItemAction):RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.item_name.setText(itemList[position].itemName)
        holder.price.setText(itemList[position].itemPrice.toString())

        itemAction.setItemImage(itemList[position].itemImgUrl.toString(),holder.item_img)

        holder.remove_img.setOnClickListener {
            itemAction.onRemoveClick(itemList[position].uid)
        }

        holder.item_img.setOnClickListener {
            itemAction.onUrlClick(itemList[position].itemURL.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_data_layout,parent,false)
        return ViewHolder(v)
    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val item_name = view.findViewById<TextView>(R.id.item_data_layout_textView_title)
        val price = view.findViewById<TextView>(R.id.item_data_layout_textView_price)
        val remove_img = view.findViewById<ImageView>(R.id.item_data_layout_imageView_delete)
        val item_img = view.findViewById<ImageView>(R.id.item_data_layout_imageView_img)
    }


}