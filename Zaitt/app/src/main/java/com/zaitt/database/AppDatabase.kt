package com.zaitt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zaitt.dao.CreditCardDao
import com.zaitt.entity.CreditCard
import com.zaitt.util.UtilConstants.APP_DATABASE
import com.zaitt.util.UtilConstants.VERSION_DATABASE


@Database(entities = [CreditCard::class], version = VERSION_DATABASE, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    //compation == static java
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            // sincronizacao evitar criar 2 instancias
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        APP_DATABASE
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}
