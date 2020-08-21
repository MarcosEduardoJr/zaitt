package com.zaitt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.model.Store
import com.zaitt.viewholder.CreditCardListViewHolder
import com.zaitt.viewholder.StoreListViewHolder

/**
 * Created by marco on 10,August,2020
 */
class CreditCardListAdapter(items: MutableList<CreditCard>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfItems = items

    private var itemClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CreditCardListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_credit_card, parent, false)
        ).apply {
            itemClick = { itemId ->
                this@CreditCardListAdapter.itemClick?.invoke(itemId)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as CreditCardListViewHolder
        itemViewHolder.bindView(listOfItems[position])
    }

    override fun getItemCount(): Int = listOfItems.size

}