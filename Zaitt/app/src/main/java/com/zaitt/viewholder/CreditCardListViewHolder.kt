package com.zaitt.viewholder

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.fragments.creditCard.ListCardsFragmentDirections
import kotlinx.android.synthetic.main.item_credit_card.view.*

/**
 * Created by marco on 10,August,2020
 */
class CreditCardListViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var itemClick: ((String) -> Unit)? = null

    fun bindView(item: CreditCard) {

        val number =
            "" + item!!.numberCard[5] + item!!.numberCard[6] + item!!.numberCard[7] + item!!.numberCard[8]
        itemView.numberCreditCard.text =
            itemView.resources.getString(R.string.label_bullet_number_credit_card, number)

        //For Handling RecyclerView Item Click
        itemView?.setOnClickListener {
            val action = ListCardsFragmentDirections.actionReturn(null, null, item)
            Navigation.findNavController(itemView).navigate(action)

        }

    }


}