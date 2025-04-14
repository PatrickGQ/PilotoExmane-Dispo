package com.example.pilotoexamen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotoexamen.data.entities.ExpenseEntity
import com.example.pilotoexamen.data.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    suspend fun addExpense(name: String, price: Double, description: String, date: Long) {
        withContext(Dispatchers.IO) {
            val expense = ExpenseEntity(
                name = name,
                price = price,
                description = description,
                date = date
            )
            repository.insertExpense(expense)
        }
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        withContext(Dispatchers.IO) {
            repository.deleteExpense(expense)
        }
    }

    suspend fun getAllExpenses(): List<ExpenseEntity> {
        return withContext(Dispatchers.IO) {
            repository.getAllExpense()
        }
    }
}