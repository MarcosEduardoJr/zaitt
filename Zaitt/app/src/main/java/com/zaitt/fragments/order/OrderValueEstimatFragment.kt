package com.zaitt.fragments.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaitt.R
import com.zaitt.model.Order
import com.zaitt.model.Store
import kotlinx.android.synthetic.main.fragment_value_estimat_order.*

class ValueEstimatOrderFragment : Fragment() {

    private lateinit var order: Order
    private lateinit var store: Store
    private val args: ValueEstimatOrderFragmentArgs by navArgs()
    private lateinit var v: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_value_estimat_order, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        store = args.store
        order = args.order
        initializeComponents()
        updateUi()
    }

    private fun initializeComponents() {
        back.setOnClickListener { findNavController().navigateUp() }
        advance.setOnClickListener {
            order.estimate = estimate.text.toString()
            nextPage()
        }
        advance.isEnabled = false
        estimate.doOnTextChanged { text, start, before, count ->
            advance.isEnabled = text.toString() != ""
        }
    }

    private fun nextPage() {
        val action =
            ValueEstimatOrderFragmentDirections.actionNext(
                store,
                order,
                null
            )
        Navigation.findNavController(v).navigate(action)
    }

    private fun updateUi() {
        img.setImageBitmap(store.bitmap)
        name.text = store.name
        address.text = store.address
        description.text = order.description
    }


}