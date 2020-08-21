package com.zaitt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaitt.R
import com.zaitt.model.Store
import com.zaitt.viewholder.StoreListViewHolder

/**
 * Created by marco on 10,August,2020
 */
class StoreListAdapter(items: MutableList<Store>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfItems = items

    private var itemClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StoreListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        ).apply {
            itemClick = { itemId ->
                this@StoreListAdapter.itemClick?.invoke(itemId)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as StoreListViewHolder
        itemViewHolder.bindView(listOfItems[position])
    }

    override fun getItemCount(): Int = listOfItems.size

    fun update(itemsNew: List<Store>){
        listOfItems.clear() // ->> optional if you need have clear of object
        listOfItems.addAll(itemsNew)
        notifyDataSetChanged()
    }
}