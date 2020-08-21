package com.zaitt.fragments.creditCard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaitt.R
import com.zaitt.entity.CreditCard
import com.zaitt.util.MaskEditUtil
import com.zaitt.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.*
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.back
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.date
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.name
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.next
import kotlinx.android.synthetic.main.fragment_new_credit_card_cpf.number
import kotlinx.android.synthetic.main.fragment_new_credit_card_cvv.*
import kotlinx.android.synthetic.main.fragment_new_credit_card_number_card.*

class NewCreditCardCpfFragment : Fragment() {

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
        v = inflater.inflate(R.layout.fragment_new_credit_card_cpf, container, false)
        return v
    }


    override fun onStart() {
        super.onStart()
        creditCard = args.creditCard
        initializeViewModel()
        initializeComponents()
        updateUi()
    }

    private fun updateUi() {
        number.text = creditCard.numberCard
        name.text = creditCard.name
        date.text = creditCard.validate
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
            creditCard.cpf = cpf.text.toString()
            creditCardViewModel.update(creditCard)
        }
        cpf.addTextChangedListener(
            MaskEditUtil.mask(
                cpf,
                MaskEditUtil.FORMAT_CPF
            )
        )
        next.isEnabled = false
        cpf.doOnTextChanged { text, start, before, count ->
            next.isEnabled = (text.toString() != "") && (cpf.text.toString().length == 14)
        }
    }

    private fun nextPage() {
        val action =
            NewCreditCardCpfFragmentDirections.actionNext(
                null,
                null,
                creditCard
            )
        Navigation.findNavController(v).navigate(action)
    }

}