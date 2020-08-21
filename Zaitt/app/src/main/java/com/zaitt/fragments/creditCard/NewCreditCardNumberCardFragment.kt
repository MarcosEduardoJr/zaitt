package com.zaitt.fragments.creditCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.util.MaskEditUtil
import com.zaitt.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.fragment_new_credit_card_number_card.*

class NewCreditCardNumberCardFragment : Fragment() {

    private lateinit var v: View
    private lateinit var creditCard: CreditCard
    private val creditCardViewModel: CreditCardViewModel by viewModels()
    private var isActionUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_new_credit_card_number_card, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        initializeViewModel()
        initializeComponents()
    }

    private fun initializeViewModel() {
        creditCardViewModel.all.observe(
            this,
            androidx.lifecycle.Observer { response ->
                if (response.isNotEmpty() && isActionUpdate) {
                    nextPage()
                }
            })


    }

    private fun initializeComponents() {
        back.setOnClickListener { findNavController().navigateUp() }
        next.setOnClickListener {
            isActionUpdate = true
            creditCard = CreditCard(number.text.toString())
            creditCard.id = creditCardViewModel.insert(creditCard)!!
        }
        next.isEnabled = false
        number.addTextChangedListener(
            MaskEditUtil.mask(
                number,
                MaskEditUtil.FORMAT_CREDIT_CARD
            )
        )
        number.doOnTextChanged { text, start, before, count ->
            next.isEnabled = (text.toString() != "") && (number.text.toString().length == 19)
            labelNumber.text = text
        }
    }

    private fun nextPage() {
        val action =
            NewCreditCardNumberCardFragmentDirections.actionNext(
                creditCard
            )
        Navigation.findNavController(v).navigate(action)
    }


}