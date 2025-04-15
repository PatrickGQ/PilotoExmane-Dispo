package com.example.pilotoexamen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotoexamen.data.entities.ExpenseEntity
import com.example.pilotoexamen.data.repository.ExpenseRepository
import com.example.pilotoexamen.util.NotificationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository,
    private val incomeViewModel: IncomeViewModel
) : ViewModel() {

    suspend fun addExpense(name: String, price: Double, description: String, date: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val totalIncome = incomeViewModel.getAllIncomes().sumOf { it.amount }
            val totalExpenses = expenseRepository.getAllExpense().sumOf { it.price }

            if (price + totalExpenses > totalIncome) {
                NotificationUtils.showNotification(
                    context = com.example.pilotoexamen.util.AppContextProvider.context,
                    title = "Advertencia de Gasto",
                    message = "No tienes ingresos suficientes para registrar este gasto."
                )
                return@withContext false
            }

            val expense = ExpenseEntity(
                name = name,
                price = price,
                description = description,
                date = date
            )
            expenseRepository.insertExpense(expense)
            return@withContext true
        }
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        withContext(Dispatchers.IO) {
            expenseRepository.deleteExpense(expense)
        }
    }

    suspend fun getAllExpenses(): List<ExpenseEntity> {
        return withContext(Dispatchers.IO) {
            expenseRepository.getAllExpense()
        }
    }

    suspend fun getTotalIncome(): Double {
        return withContext(Dispatchers.IO) {
            incomeViewModel.getAllIncomes().sumOf { it.amount }
        }
    }

    suspend fun getTotalExpenses(): Double {
        return withContext(Dispatchers.IO) {
            expenseRepository.getAllExpense().sumOf { it.price }
        }
    }
}