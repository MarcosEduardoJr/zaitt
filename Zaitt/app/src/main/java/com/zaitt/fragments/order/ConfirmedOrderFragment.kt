package com.zaitt.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.zaitt.MainActivity
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.model.Checkout
import com.zaitt.model.CloseOrder
import com.zaitt.model.Order
import com.zaitt.model.Store
import com.zaitt.viewmodel.SalesManagerViewModel
import kotlinx.android.synthetic.main.fragment_confirmed_order.*

class ConfirmedOrderFragment : Fragment() {

    private var order: Order? = null
    private var store: Store? = null
    private var creditCard: CreditCard? = null
    private val args: ConfirmedOrderFragmentArgs by navArgs()
    private val salesManagerViewModel: SalesManagerViewModel by viewModels()
    private lateinit var v: View
    lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = (requireActivity() as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_confirmed_order, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        fetchStoreOrder()
        initializeViewModel()
    }

    private fun initializeViewModel() {
        salesManagerViewModel.closeOrder.observe(
            this,
            androidx.lifecycle.Observer { response ->
                if (response != null) {
                    updateUi()
                    Snackbar.make(requireView(), response.message, Snackbar.LENGTH_LONG).show()
                }
            })
        salesManagerViewModel.closeOrder(initiatializeCloseOrder())
    }

    private fun initiatializeCloseOrder(): CloseOrder {
        var closeOrder = CloseOrder()
        closeOrder.store_latitude = store?.storeLatitude!!
        closeOrder.store_longitude = store?.storeLongitude!!
        closeOrder.user_latitude = activity.userLatlng.latitude
        closeOrder.user_longitude = activity.userLatlng.longitude
        closeOrder.value = order?.estimate?.toDouble()!!
        closeOrder.card_number = creditCard?.numberCard.toString()
        closeOrder.cvv = creditCard?.cvv.toString()
        closeOrder.expiry_date = creditCard?.validate.toString()

        return closeOrder
    }

    private fun fetchStoreOrder() {
        if (args.store != null && args.order != null) {
            store = args.store!!
            order = args.order!!
            creditCard = args.creditCard!!
        }
    }

    private fun updateUi() {
        if (store != null && order != null) {
            img.setImageBitmap(store!!.bitmap)
            name.text = store!!.name
            address.text = store!!.address
            description.text = order!!.description
            estimate.text = getString(R.string.label_value_estimate, order!!.estimate)
        }


    }


}