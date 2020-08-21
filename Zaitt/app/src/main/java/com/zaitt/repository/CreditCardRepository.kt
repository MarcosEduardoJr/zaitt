package com.zaitt.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.zaitt.dao.CreditCardDao
import com.zaitt.database.AppDatabase
import com.zaitt.entity.CreditCard

class CreditCardRepository {
    lateinit var creditCardDao: CreditCardDao

    lateinit var list: LiveData<List<CreditCard>>


    //quando singleton necessita de um context pasamos um application
    constructor(application: Application) {
        var database: AppDatabase = AppDatabase.getInstance(application)!!
        creditCardDao = database.creditCardDao()
        list = creditCardDao.all
    }

    fun insert(param: CreditCard): Long? {
        return InsertAsyncTask(creditCardDao).execute(param).get()
    }

    fun update(param: CreditCard) {
        UpdateAsyncTask(creditCardDao).execute(param)
    }


    fun getAllData(): LiveData<List<CreditCard>> {
        return list;
    }

    companion object {

        class InsertAsyncTask(noteDao: CreditCardDao) : AsyncTask<CreditCard, Void, Long>() {
            var dao: CreditCardDao? = noteDao

            override fun doInBackground(vararg params: CreditCard?): Long? {
                return dao?.insert(params[0])

            }

        }

        class UpdateAsyncTask(noteDao: CreditCardDao) : AsyncTask<CreditCard, Void, Long>() {
            var dao: CreditCardDao? = noteDao

            override fun doInBackground(vararg params: CreditCard): Long? {
                dao?.update(params[0])
                return null
            }

        }

    }


}