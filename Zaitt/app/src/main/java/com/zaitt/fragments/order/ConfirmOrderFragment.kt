package com.zaitt.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaitt.MainActivity
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.model.Checkout
import com.zaitt.model.Order
import com.zaitt.model.Store
import com.zaitt.viewmodel.CreditCardViewModel
import com.zaitt.viewmodel.SalesManagerViewModel
import kotlinx.android.synthetic.main.fragment_confirm_order.*

class ConfirmOrderFragment : Fragment() {
    private var creditCard: CreditCard? = null
    private var order: Order? = null
    private var store: Store? = null
    private val args: ConfirmOrderFragmentArgs by navArgs()
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
        v = inflater.inflate(R.layout.fragment_confirm_order, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        fetchStoreOrderCreditCard()
        initializeViewModel()
    }

    private fun initializeViewModel() {
        salesManagerViewModel.checkout.observe(
            this,
            androidx.lifecycle.Observer { response ->
                if (response != null) {
                    initializeComponents()
                    updateUi()
                }
            })
        salesManagerViewModel.resume(initiatializeCheckout())
    }

    private fun initiatializeCheckout(): Checkout {
        var checkout = Checkout()
        checkout.store_latitude = store?.storeLatitude!!
        checkout.store_longitude = store?.storeLongitude!!
        checkout.user_latitude = activity.userLatlng.latitude
        checkout.user_longitude = activity.userLatlng.longitude
        checkout.value = order?.estimate?.toDouble()!!
        return checkout
    }

    private fun fetchStoreOrderCreditCard() {
        val main = (activity as MainActivity)
        if (args.creditCard != null) {
            creditCard = args.creditCard
        }
        if (args.store != null && args.order != null) {
            main.store = args.store!!
            main.order = args.order!!
        }
        if (main.store != null && main.order != null) {
            store = main.store!!
            order = main.order!!
        }


    }

    private fun initializeComponents() {
        back.setOnClickListener { findNavController().navigateUp() }
        choosePayment.setOnClickListener { nextPageChoosePayment() }
        numberCreditCard.setOnClickListener { nextPageChoosePayment() }
        confirmOrder.setOnClickListener {
            nextPage()
        }
        if (creditCard == null)
            confirmOrder.isEnabled = false

    }

    private fun nextPageChoosePayment() {
        Navigation.findNavController(v).navigate(R.id.action_next_list_cards)
    }

    private fun nextPage() {
        val action =
            ConfirmOrderFragmentDirections.actionNext(
                store,
                order,
                creditCard
            )
        Navigation.findNavController(v).navigate(action)
    }

    private fun updateUi() {
        if (store != null && order != null) {
            img.setImageBitmap(store!!.bitmap)
            name.text = store!!.name
            address.text = store!!.address
            description.text = order!!.description
            estimate.text = getString(R.string.label_value_estimate, order!!.estimate)
        }
        if (creditCard != null) {
            choosePayment.visibility = View.GONE
            numberCreditCard.visibility = View.VISIBLE
            flag.visibility = View.VISIBLE
            val number =
                "" + creditCard!!.numberCard[5] + creditCard!!.numberCard[6] + creditCard!!.numberCard[7] + creditCard!!.numberCard[8]
            numberCreditCard.text = getString(R.string.label_bullet_number_credit_card, number)

        } else {
            choosePayment.visibility = View.VISIBLE
            numberCreditCard.visibility = View.GONE
            flag.visibility = View.GONE
        }

    }
}