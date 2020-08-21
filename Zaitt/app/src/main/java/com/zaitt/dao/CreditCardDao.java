package com.zaitt.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zaitt.entity.CreditCard;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by marco on 15,Setembro,2019
 */
@Dao
public interface CreditCardDao {

    //GET ---------------------------------------------------------------

    @Query("SELECT * FROM credit_card")
    LiveData<List<CreditCard>> getAll();

    @Query("select * from credit_card where id = :id")
    CreditCard getById(Long id);

    //INSERT ---------------------------------------------------------------

    @Insert
    long insert(@Nullable CreditCard creditCard);

    //UPDATE ---------------------------------------------------------------

    @Update
    void update(CreditCard creditCard);

    //DELETE ---------------------------------------------------------------

    @Delete
    void delete(CreditCard... creditCards);


    @Query("DELETE FROM credit_card")
    void deleteAll();
}
