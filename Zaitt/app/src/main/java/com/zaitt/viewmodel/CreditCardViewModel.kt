package com.zaitt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zaitt.entity.CreditCard
import com.zaitt.repository.CreditCardRepository

class CreditCardViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: CreditCardRepository = CreditCardRepository(application)
       var all: LiveData<List<CreditCard>>

    init {
        all = repository.getAllData()
    }

    fun insert(param: CreditCard): Long? {
        return repository.insert(param)
    }

    fun update(param: CreditCard) {
        repository.update(param)
    }

    fun getAllData(): LiveData<List<CreditCard>> {
        return all
    }

}