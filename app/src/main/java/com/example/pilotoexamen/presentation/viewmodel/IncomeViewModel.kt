package com.example.pilotoexamen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pilotoexamen.data.entities.IncomeEntity
import com.example.pilotoexamen.data.repository.IncomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeViewModel(private val repository: IncomeRepository) : ViewModel() {

    suspend fun addIncome(name: String, amount: Double, description: String, date: Long) {
        withContext(Dispatchers.IO) {
            repository.insertIncome(
                IncomeEntity(
                    name = name,
                    amount = amount,
                    description = description,
                    date = date
                )
            )
        }
    }

    suspend fun deleteIncome(income: IncomeEntity) {
        withContext(Dispatchers.IO) {
            repository.deleteIncome(income)
        }
    }

    suspend fun getAllIncomes(): List<IncomeEntity> {
        return withContext(Dispatchers.IO) {
            repository.getAllIncomes()
        }
    }
}