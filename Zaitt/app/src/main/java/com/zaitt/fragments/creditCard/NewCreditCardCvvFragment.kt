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
import androidx.navigation.fragment.navArgs
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.fragment_new_credit_card_cvv.*


class NewCreditCardCvvFragment : Fragment() {

    private lateinit var v: View
    private lateinit var creditCard: CreditCard
    private val creditCardViewModel: CreditCardViewModel by viewModels()
    private val args: NewCreditCardDueDateFragmentArgs by navArgs()
    private var isActionUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_new_credit_card_cvv, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        creditCard = args.creditCard
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
            creditCard.cvv = cvv.text.toString()
            creditCardViewModel.update(creditCard)
        }
        next.isEnabled = false
        cvv.doOnTextChanged { text, start, before, count ->
            next.isEnabled = (text.toString() != "")
            if (cvv.text.toString().length <= 3)
                label_cvv.text = text
        }
    }

    private fun nextPage() {
        val action =
            NewCreditCardDueDateFragmentDirections.actionNext(creditCard)
        Navigation.findNavController(v).navigate(action)
    }

}