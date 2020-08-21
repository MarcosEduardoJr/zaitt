package com.zaitt.fragments.creditCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaitt.R
import com.zaitt.adapter.CreditCardListAdapter
import com.zaitt.entity.CreditCard
import com.zaitt.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.fragment_list_cards.*

class ListCardsFragment : Fragment() {

    private val creditCardViewModel: CreditCardViewModel by viewModels()
    private lateinit var v: View
    private lateinit var viewAdapter: CreditCardListAdapter
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_list_cards, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        initializeViewModel()
        initializeComponents()
    }

    private fun initializeComponents() {
        back.setOnClickListener { findNavController().navigateUp() }
        newCreditCard.setOnClickListener {
            Navigation.findNavController(v).navigate(R.id.action_next)
        }
    }

    private fun initializeViewModel() {
        creditCardViewModel.all.observe(
            this,
            androidx.lifecycle.Observer { response ->
                updateUi(response)
            })
        creditCardViewModel.getAllData()

    }

    private fun updateUi(response: List<CreditCard>) {
        if (response.isEmpty()) {
            cardEmptyList.visibility = View.VISIBLE
            listCard.visibility = View.GONE
        } else {
            cardEmptyList.visibility = View.GONE
            listCard.visibility = View.VISIBLE
            initializeRecycleView()
        }
    }

    private fun initializeRecycleView() {
        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = CreditCardListAdapter(creditCardViewModel.all.value!!.toMutableList())

        recyclerview.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }


}