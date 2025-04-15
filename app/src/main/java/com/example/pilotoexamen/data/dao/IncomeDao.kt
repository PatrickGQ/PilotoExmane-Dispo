package com.example.pilotoexamen.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pilotoexamen.data.entities.IncomeEntity

@Dao
interface IncomeDao {

    @Insert
    suspend fun insertIncome(income: IncomeEntity)

    @Delete
    suspend fun deleteIncome(income: IncomeEntity)

    @Query("SELECT * FROM income_table ORDER BY date DESC")
    suspend fun getAllIncomes() : List<IncomeEntity>
}