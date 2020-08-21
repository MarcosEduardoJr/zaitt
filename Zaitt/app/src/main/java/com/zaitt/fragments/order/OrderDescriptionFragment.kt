package com.zaitt.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaitt.R
import com.zaitt.model.Order
import com.zaitt.model.Store
import kotlinx.android.synthetic.main.fragment_store_order_description.*

class OrderDescriptionFragment : Fragment() {

    private lateinit var v: View
    private val order: Order = Order()
    private lateinit var store: Store
    private val args: OrderDescriptionFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_store_order_description, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        store = args.store
        initializeComponents()
        updateUi()
    }

    private fun initializeComponents() {
        back.setOnClickListener { findNavController().navigateUp() }
        advance.setOnClickListener {
            order.description = description.text.toString()
            nextPage()
        }
        advance.isEnabled = false
        description.doOnTextChanged { text, start, before, count ->
            advance.isEnabled = text.toString() != ""
        }
    }

    private fun nextPage() {
        val action =
            OrderDescriptionFragmentDirections.actionNext(
                store,
                order
            )
        Navigation.findNavController(v).navigate(action)
    }

    private fun updateUi() {
        img.setImageBitmap(store.bitmap)
        name.text = store.name
        address.text = store.address
    }


}
