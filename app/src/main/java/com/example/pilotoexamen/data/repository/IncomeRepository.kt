package com.example.pilotoexamen.data.repository

import com.example.pilotoexamen.data.dao.IncomeDao
import com.example.pilotoexamen.data.entities.IncomeEntity

class IncomeRepository(private val incomeDao: IncomeDao) {

    suspend fun insertIncome(income: IncomeEntity) {
        incomeDao.insertIncome(income)
    }

    suspend fun deleteIncome(income: IncomeEntity) {
        incomeDao.deleteIncome(income)
    }

    suspend fun getAllIncomes(): List<IncomeEntity> {
        return incomeDao.getAllIncomes()
    }

    suspend fun getTotalIncome(): Double {
        return incomeDao.getAllIncomes().sumOf { it.amount }
    }
}