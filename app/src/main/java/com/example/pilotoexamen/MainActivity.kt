package com.example.pilotoexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pilotoexamen.data.AppDatabase
import com.example.pilotoexamen.data.repository.ExpenseRepository
import com.example.pilotoexamen.presentation.screens.ExpenseScreen
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModel
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModelFactory
import com.example.pilotoexamen.ui.theme.PilotoExamenTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = ExpenseRepository(database.expenseDao())

        val viewModelFactory = ExpenseViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[ExpenseViewModel::class.java]
        setContent {
            PilotoExamenTheme {
                ExpenseScreen(viewModel = viewModel)
            }
        }
    }
}