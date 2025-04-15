package com.example.pilotoexamen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pilotoexamen.data.repository.ExpenseRepository
import com.example.pilotoexamen.data.repository.IncomeRepository

class ExpenseViewModelFactory(
    private val expenseRepository: ExpenseRepository,
    private val incomeViewModel: IncomeViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            return ExpenseViewModel(expenseRepository, incomeViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}