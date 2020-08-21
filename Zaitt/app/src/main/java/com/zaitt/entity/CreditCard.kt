package com.zaitt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by marco on 17,Setembro,2019
 */

@Entity(tableName = "credit_card")
data class CreditCard(
    @ColumnInfo(name = "number_card")
    var numberCard: String
) : Serializable {
    @PrimaryKey(autoGenerate = true) // caso queira mudar o id @columnInfo ( name  = "")
    var id: Long = 0

    @ColumnInfo(name = "name")
    var name: String? = ""

    @ColumnInfo(name = "validate")
    var validate: String? = ""

    @ColumnInfo(name = "cvv")
    var cvv: String? = ""

    @ColumnInfo(name = "cpf")
    var cpf: String? = ""

}