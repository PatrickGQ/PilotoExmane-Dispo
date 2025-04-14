package com.example.pilotoexamen.data.repository

import com.example.pilotoexamen.data.dao.ExpenseDao
import com.example.pilotoexamen.data.entities.ExpenseEntity

class ExpenseRepository(private  val expenseDao: ExpenseDao) {

    suspend fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    fun getAllExpense(): List<ExpenseEntity> {
        return expenseDao.getAllExpenses()
    }
}